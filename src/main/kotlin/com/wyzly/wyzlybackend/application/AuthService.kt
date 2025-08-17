package com.wyzly.wyzlybackend.application

import com.wyzly.wyzlybackend.controller.dto.LoginResponse
import com.wyzly.wyzlybackend.controller.dto.UserResponse
import com.wyzly.wyzlybackend.domain.entity.UserEntity
import com.wyzly.wyzlybackend.domain.entity.UserRole
import com.wyzly.wyzlybackend.infrastructure.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class AuthService(private val userRepository: UserJpaRepository) {

    // In a real application, we would use a proper JWT library
    // For this mock implementation, we'll just store tokens in memory
    private val tokenStore = ConcurrentHashMap<String, String>() // token -> userId

    fun login(email: String, password: String): LoginResponse {
        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("Invalid credentials")

        if (user.password != password) {
            throw RuntimeException("Invalid credentials")
        }

        // Generate a token
        val token = generateToken(user.id!!)

        return LoginResponse(
            user = UserResponse.fromEntity(user),
            token = token
        )
    }

    fun register(email: String, password: String, name: String, role: UserRole): LoginResponse {
        if (userRepository.existsByEmail(email)) {
            throw RuntimeException("Email already in use")
        }

        val user = UserEntity.createUser(email, password, name, role)
        val savedUser = userRepository.save(user)

        // Generate a token
        val token = generateToken(savedUser.id!!)

        return LoginResponse(
            user = UserResponse.fromEntity(savedUser),
            token = token
        )
    }

    fun logout(token: String) {
        tokenStore.remove(token)
    }

    fun getCurrentUser(token: String): UserResponse {
        val userId = getUserIdFromToken(token)
            ?: throw RuntimeException("Invalid token")

        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }

        return UserResponse.fromEntity(user)
    }

    private fun generateToken(userId: Long): String {
        val token = UUID.randomUUID().toString()
        tokenStore[token] = userId.toString()
        return token
    }

    private fun getUserIdFromToken(token: String): String? {
        return tokenStore[token]
    }
}
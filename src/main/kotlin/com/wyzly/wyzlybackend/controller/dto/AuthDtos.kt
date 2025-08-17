package com.wyzly.wyzlybackend.controller.dto

import com.wyzly.wyzlybackend.domain.entity.UserEntity
import com.wyzly.wyzlybackend.domain.entity.UserRole
import java.time.format.DateTimeFormatter

// Request DTOs
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val role: UserRole
)

// Response DTOs
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String? = null
) {

    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

        fun fromEntity(user: UserEntity): UserResponse {
            return UserResponse(
                id = user.id!!,
                email = user.email,
                name = user.name,
                role = user.role.name,
                createdAt = user.createdAt.format(dateTimeFormatter),
                updatedAt = user.updatedAt.format(dateTimeFormatter)
            )
        }
    }

}

data class LoginResponse(
    val user: UserResponse,
    val token: String
)
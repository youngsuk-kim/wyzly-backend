package com.wyzly.wyzlybackend.infrastructure.persistence.repository

import com.wyzly.wyzlybackend.domain.entity.UserEntity
import com.wyzly.wyzlybackend.domain.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?
    fun findByRole(role: UserRole): List<UserEntity>
    fun existsByEmail(email: String): Boolean
}

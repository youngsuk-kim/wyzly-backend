package com.wyzly.wyzlybackend.infrastructure.persistence.repository

import com.wyzly.wyzlybackend.domain.entity.OrderEntity
import com.wyzly.wyzlybackend.domain.entity.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, String> {
    fun findByCustomerId(customerId: Long): List<OrderEntity>
    fun findByStatus(status: OrderStatus): List<OrderEntity>
}

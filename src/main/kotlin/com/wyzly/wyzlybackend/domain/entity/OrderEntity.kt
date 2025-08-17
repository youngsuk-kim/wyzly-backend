package com.wyzly.wyzlybackend.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val boxId: String,

    @Column(nullable = false)
    val customerId: Long,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: OrderStatus,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateStatus(newStatus: OrderStatus): OrderEntity {
        return OrderEntity(
            id = this.id,
            boxId = this.boxId,
            customerId = this.customerId,
            quantity = this.quantity,
            totalPrice = this.totalPrice,
            status = newStatus,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now()
        )
    }

    companion object {
        fun create(boxId: String, customerId: Long, quantity: Int, totalPrice: Double): OrderEntity {
            val now = LocalDateTime.now()
            return OrderEntity(
                boxId = boxId,
                customerId = customerId,
                quantity = quantity,
                totalPrice = totalPrice,
                status = OrderStatus.PENDING,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}

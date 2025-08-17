package com.wyzly.wyzlybackend.controller.dto

import com.wyzly.wyzlybackend.domain.entity.OrderEntity
import java.time.format.DateTimeFormatter

// Request DTOs
data class CreateOrderRequest(
    val boxId: String,
    val quantity: Int
)

data class UpdateOrderStatusRequest(
    val status: String
)

// Response DTOs
data class OrderResponse(
    val id: Long,
    val boxId: String,
    val userId: Long,
    val quantity: Int,
    val totalPrice: Double,
    val status: String,
    val createdAt: String,
    val updatedAt: String? = null
) {
    companion object {
        fun fromEntity(orderEntity: OrderEntity, dateTimeFormatter: DateTimeFormatter): OrderResponse {
            return OrderResponse(
                id = orderEntity.id!!,
                boxId = orderEntity.boxId,
                userId = orderEntity.customerId,
                quantity = orderEntity.quantity,
                totalPrice = orderEntity.totalPrice,
                status = orderEntity.status.name,
                createdAt = orderEntity.createdAt.format(dateTimeFormatter),
                updatedAt = orderEntity.updatedAt.format(dateTimeFormatter)
            )
        }
    }
}

// Extended response with box details
data class OrderWithBoxResponse(
    val id: Long,
    val box: BoxResponse,
    val userId: Long,
    val quantity: Int,
    val totalPrice: Double,
    val status: String,
    val createdAt: String,
    val updatedAt: String? = null
) {
    companion object {
        fun fromEntityAndBox(
            orderEntity: OrderEntity,
            boxResponse: BoxResponse, 
            dateTimeFormatter: DateTimeFormatter
        ): OrderWithBoxResponse {
            return OrderWithBoxResponse(
                id = orderEntity.id!!,
                box = boxResponse,
                userId = orderEntity.customerId,
                quantity = orderEntity.quantity,
                totalPrice = orderEntity.totalPrice,
                status = orderEntity.status.name,
                createdAt = orderEntity.createdAt.format(dateTimeFormatter),
                updatedAt = orderEntity.updatedAt.format(dateTimeFormatter)
            )
        }
    }
}

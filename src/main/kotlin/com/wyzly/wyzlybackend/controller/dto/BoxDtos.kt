package com.wyzly.wyzlybackend.controller.dto

import com.wyzly.wyzlybackend.domain.entity.BoxEntity
import java.time.format.DateTimeFormatter

// Request DTOs
data class CreateBoxRequest(
    val title: String,
    val price: Double,
    val quantity: Int,
    val image: String
)

data class UpdateBoxRequest(
    val title: String? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val image: String? = null
)

data class UpdateQuantityRequest(
    val quantity: Int
)

// Response DTOs
data class BoxResponse(
    val id: Long,
    val title: String,
    val price: Double,
    val quantity: Int,
    val image: String,
    val restaurantId: String,
    val createdAt: String,
    val updatedAt: String? = null
) {
    companion object {
        fun fromEntity(boxEntity: BoxEntity, dateTimeFormatter: DateTimeFormatter): BoxResponse {
            return BoxResponse(
                id = boxEntity.id!!,
                title = boxEntity.title,
                price = boxEntity.price,
                quantity = boxEntity.quantity,
                image = boxEntity.image,
                restaurantId = boxEntity.restaurantId,
                createdAt = boxEntity.createdAt.format(dateTimeFormatter),
                updatedAt = boxEntity.updatedAt.format(dateTimeFormatter)
            )
        }
    }
}

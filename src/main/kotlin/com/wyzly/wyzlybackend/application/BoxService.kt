package com.wyzly.wyzlybackend.application

import com.wyzly.wyzlybackend.controller.dto.BoxResponse
import com.wyzly.wyzlybackend.domain.entity.BoxEntity
import com.wyzly.wyzlybackend.infrastructure.persistence.repository.BoxJpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class BoxService(private val boxRepository: BoxJpaRepository) {

    private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun getAllBoxes(): List<BoxResponse> {
        return boxRepository.findAll().map { BoxResponse.fromEntity(it, dateTimeFormatter) }
    }

    fun getBoxById(id: String): BoxResponse {
        val box = boxRepository.findById(id)
            .orElseThrow { RuntimeException("Box not found with id: $id") }

        return BoxResponse.fromEntity(box, dateTimeFormatter)
    }

    fun getBoxesByRestaurantId(restaurantId: String): List<BoxResponse> {
        return boxRepository.findByRestaurantId(restaurantId).map { BoxResponse.fromEntity(it, dateTimeFormatter) }
    }

    fun createBox(title: String, price: Double, quantity: Int, image: String, restaurantId: String): BoxResponse {
        val now = LocalDateTime.now()
        val box = BoxEntity(
            title = title,
            price = price,
            quantity = quantity,
            image = image,
            restaurantId = restaurantId,
            createdAt = now,
            updatedAt = now
        )

        val savedBox = boxRepository.save(box)
        return BoxResponse.fromEntity(savedBox, dateTimeFormatter)
    }

    fun updateBox(id: String, title: String?, price: Double?, quantity: Int?, image: String?): BoxResponse {
        val existingBox = boxRepository.findById(id)
            .orElseThrow { RuntimeException("Box not found with id: $id") }

        val updatedBox = existingBox.updateBox(
            title = title,
            price = price,
            quantity = quantity,
            image = image
        )

        val savedBox = boxRepository.save(updatedBox)
        return BoxResponse.fromEntity(savedBox, dateTimeFormatter)
    }

    fun deleteBox(id: String) {
        if (!boxRepository.existsById(id)) {
            throw RuntimeException("Box not found with id: $id")
        }

        boxRepository.deleteById(id)
    }

    fun updateQuantity(id: String, quantity: Int): BoxResponse {
        val existingBox = boxRepository.findById(id)
            .orElseThrow { RuntimeException("Box not found with id: $id") }

        val updatedBox = existingBox.updateQuantity(quantity)

        val savedBox = boxRepository.save(updatedBox)
        return BoxResponse.fromEntity(savedBox, dateTimeFormatter)
    }
}

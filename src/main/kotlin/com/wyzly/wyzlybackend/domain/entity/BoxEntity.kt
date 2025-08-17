package com.wyzly.wyzlybackend.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boxes")
data class BoxEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val price: Double,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val image: String,

    @Column(nullable = false)
    val restaurantId: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateBox(
        title: String? = null,
        price: Double? = null,
        quantity: Int? = null,
        image: String? = null
    ): BoxEntity {
        return BoxEntity(
            id = this.id,
            title = title ?: this.title,
            price = price ?: this.price,
            quantity = quantity ?: this.quantity,
            image = image ?: this.image,
            restaurantId = this.restaurantId,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now()
        )
    }

    fun updateQuantity(quantity: Int): BoxEntity {
        return this.updateBox(quantity = quantity)
    }
}

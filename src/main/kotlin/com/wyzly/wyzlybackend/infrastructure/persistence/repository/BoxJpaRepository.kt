package com.wyzly.wyzlybackend.infrastructure.persistence.repository

import com.wyzly.wyzlybackend.domain.entity.BoxEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BoxJpaRepository : JpaRepository<BoxEntity, String> {
    fun findByRestaurantId(restaurantId: String): List<BoxEntity>

    @Query("SELECT b FROM BoxEntity b WHERE b.quantity > 0")
    fun findAvailable(): List<BoxEntity>
}

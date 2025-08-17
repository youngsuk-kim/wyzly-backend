package com.wyzly.wyzlybackend.domain.entity

/**
 * - **Restaurant**: Create, update, delete a "Wyzly Box" (title, price, quantity, image).
 * - **Customer**: View boxes, purchase (mock payment), view order history.
 * - **Admin**: View all orders, mark them as complete.
 */

enum class UserRole {
    ADMIN,
    RESTAURANT,
    CUSTOMER
}
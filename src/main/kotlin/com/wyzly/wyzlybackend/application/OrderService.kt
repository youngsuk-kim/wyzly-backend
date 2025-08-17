package com.wyzly.wyzlybackend.application

import com.wyzly.wyzlybackend.controller.dto.OrderResponse
import com.wyzly.wyzlybackend.controller.dto.OrderWithBoxResponse
import com.wyzly.wyzlybackend.domain.entity.OrderEntity
import com.wyzly.wyzlybackend.domain.entity.OrderStatus
import com.wyzly.wyzlybackend.infrastructure.persistence.repository.BoxJpaRepository
import com.wyzly.wyzlybackend.infrastructure.persistence.repository.OrderJpaRepository
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class OrderService(
    private val orderRepository: OrderJpaRepository,
    private val boxRepository: BoxJpaRepository,
    private val boxService: BoxService
) {

    private val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun getAllOrders(): List<OrderResponse> {
        return orderRepository.findAll().map { OrderResponse.fromEntity(it, dateTimeFormatter) }
    }

    fun getOrderById(id: String): OrderResponse {
        val order = orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order not found with id: $id") }

        return OrderResponse.fromEntity(order, dateTimeFormatter)
    }

    fun getOrdersByUserId(userId: Long): List<OrderResponse> {
        return orderRepository.findByCustomerId(userId).map { OrderResponse.fromEntity(it, dateTimeFormatter) }
    }

    fun getOrdersWithBoxDetailsByUserId(userId: Long): List<OrderWithBoxResponse> {
        return orderRepository.findByCustomerId(userId).map { order ->
            val box = boxService.getBoxById(order.boxId)

            OrderWithBoxResponse.fromEntityAndBox(order, box, dateTimeFormatter)
        }
    }

    fun createOrder(boxId: String, userId: Long, quantity: Int): OrderResponse {
        val box = boxRepository.findById(boxId)
            .orElseThrow { RuntimeException("Box not found with id: $boxId") }

        if (box.quantity < quantity) {
            throw RuntimeException("Not enough boxes available. Available: ${box.quantity}, Requested: $quantity")
        }

        // Calculate total price
        val totalPrice = box.price * quantity

        // Create the order
        val order = OrderEntity.create(
            boxId = boxId,
            customerId = userId,
            quantity = quantity,
            totalPrice = totalPrice
        )

        // Save the order
        val savedOrder = orderRepository.save(order)

        boxService.updateQuantity(boxId, box.quantity - quantity)

        return OrderResponse.fromEntity(savedOrder, dateTimeFormatter)
    }

    fun updateOrderStatus(id: String, status: String): OrderResponse {
        val orderStatus = try {
            OrderStatus.valueOf(status.uppercase())
        } catch (_: IllegalArgumentException) {
            throw RuntimeException("Invalid order status: $status")
        }

        val existingOrder = orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order not found with id: $id") }

        val updatedOrder = existingOrder.updateStatus(orderStatus)

        val savedOrder = orderRepository.save(updatedOrder)
        return OrderResponse.fromEntity(savedOrder, dateTimeFormatter)
    }
}

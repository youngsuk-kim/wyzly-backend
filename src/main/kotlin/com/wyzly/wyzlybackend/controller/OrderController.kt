package com.wyzly.wyzlybackend.controller

import com.wyzly.wyzlybackend.controller.dto.CreateOrderRequest
import com.wyzly.wyzlybackend.controller.dto.OrderResponse
import com.wyzly.wyzlybackend.controller.dto.OrderWithBoxResponse
import com.wyzly.wyzlybackend.controller.dto.UpdateOrderStatusRequest
import com.wyzly.wyzlybackend.application.AuthService
import com.wyzly.wyzlybackend.application.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = ["*"])
@Tag(name = "Order", description = "Order management APIs")
class OrderController(
    private val orderService: OrderService,
    private val authService: AuthService
) {

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders (admin only)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderResponse::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden - requires admin role")
    )
    fun getAllOrders(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<List<OrderResponse>> {
        // In a real application, we would check if the user is an admin
        val user = authService.getCurrentUser(token)
        if (user.role != "ADMIN") {
            return ResponseEntity.status(403).build()
        }

        val orders = orderService.getAllOrders()
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve a specific order by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved the order",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderResponse::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Order not found")
    )
    fun getOrderById(
        @Parameter(description = "ID of the order to retrieve", required = true)
        @PathVariable id: String,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<OrderResponse> {
        // In a real application, we would check if the user is authorized to view this order
        val order = orderService.getOrderById(id)
        return ResponseEntity.ok(order)
    }

    @GetMapping("/user")
    @Operation(summary = "Get user's orders", description = "Retrieve all orders for the current authenticated user")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved user's orders",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderResponse::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    )
    fun getOrdersByUser(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<List<OrderResponse>> {
        val user = authService.getCurrentUser(token)
        val orders = orderService.getOrdersByUserId(user.id)
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/user/with-box-details")
    @Operation(summary = "Get user's orders with box details", 
               description = "Retrieve all orders with detailed box information for the current authenticated user")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved user's orders with box details",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderWithBoxResponse::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    )
    fun getOrdersWithBoxDetailsByUser(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<List<OrderWithBoxResponse>> {
        val user = authService.getCurrentUser(token)
        val orders = orderService.getOrdersWithBoxDetailsByUserId(user.id)
        return ResponseEntity.ok(orders)
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order for the current authenticated user")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Order successfully created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Box not found")
    )
    fun createOrder(
        @Parameter(description = "Order details", required = true)
        @RequestBody request: CreateOrderRequest,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<OrderResponse> {
        val user = authService.getCurrentUser(token)

        val order = orderService.createOrder(
            boxId = request.boxId,
            userId = user.id,
            quantity = request.quantity
        )

        return ResponseEntity.ok(order)
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "Update the status of an existing order (admin or restaurant only)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Order status successfully updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = OrderResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid status"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden - requires admin or restaurant role"),
        ApiResponse(responseCode = "404", description = "Order not found")
    )
    fun updateOrderStatus(
        @Parameter(description = "ID of the order to update", required = true)
        @PathVariable id: String,

        @Parameter(description = "New status details", required = true)
        @RequestBody request: UpdateOrderStatusRequest,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<OrderResponse> {
        // In a real application, we would check if the user is authorized to update this order
        val user = authService.getCurrentUser(token)
        if (user.role != "ADMIN" && user.role != "RESTAURANT") {
            return ResponseEntity.status(403).build()
        }

        val order = orderService.updateOrderStatus(id, request.status)
        return ResponseEntity.ok(order)
    }
}

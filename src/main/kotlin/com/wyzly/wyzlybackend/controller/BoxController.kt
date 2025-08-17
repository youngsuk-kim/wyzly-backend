package com.wyzly.wyzlybackend.controller

import com.wyzly.wyzlybackend.controller.dto.BoxResponse
import com.wyzly.wyzlybackend.controller.dto.CreateBoxRequest
import com.wyzly.wyzlybackend.controller.dto.UpdateBoxRequest
import com.wyzly.wyzlybackend.controller.dto.UpdateQuantityRequest
import com.wyzly.wyzlybackend.application.BoxService
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
@RequestMapping("/api/boxes")
@CrossOrigin(origins = ["*"])
@Tag(name = "Box", description = "Box management APIs")
class BoxController(private val boxService: BoxService) {

    @GetMapping
    @Operation(summary = "Get all boxes", description = "Retrieve a list of all available boxes")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved list of boxes",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))])
    )
    fun getAllBoxes(): ResponseEntity<List<BoxResponse>> {
        val boxes = boxService.getAllBoxes()
        return ResponseEntity.ok(boxes)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get box by ID", description = "Retrieve a specific box by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved the box",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))]),
        ApiResponse(responseCode = "404", description = "Box not found")
    )
    fun getBoxById(
        @Parameter(description = "ID of the box to retrieve", required = true)
        @PathVariable id: String
    ): ResponseEntity<BoxResponse> {
        val box = boxService.getBoxById(id)
        return ResponseEntity.ok(box)
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Get boxes by restaurant ID", description = "Retrieve all boxes for a specific restaurant")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved boxes for the restaurant",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))]),
        ApiResponse(responseCode = "404", description = "Restaurant not found")
    )
    fun getBoxesByRestaurantId(
        @Parameter(description = "ID of the restaurant to retrieve boxes for", required = true)
        @PathVariable restaurantId: String
    ): ResponseEntity<List<BoxResponse>> {
        val boxes = boxService.getBoxesByRestaurantId(restaurantId)
        return ResponseEntity.ok(boxes)
    }

    @PostMapping
    @Operation(summary = "Create a new box", description = "Create a new box for a restaurant")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Box successfully created",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    )
    fun createBox(
        @Parameter(description = "Box details to create", required = true)
        @RequestBody request: CreateBoxRequest,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<BoxResponse> {
        // In a real application, we would extract the restaurant ID from the token
        // For this mock implementation, we'll use a fixed restaurant ID
        val restaurantId = "1"

        val box = boxService.createBox(
            title = request.title,
            price = request.price,
            quantity = request.quantity,
            image = request.image,
            restaurantId = restaurantId
        )

        return ResponseEntity.ok(box)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a box", description = "Update an existing box by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Box successfully updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Box not found")
    )
    fun updateBox(
        @Parameter(description = "ID of the box to update", required = true)
        @PathVariable id: String,

        @Parameter(description = "Updated box details", required = true)
        @RequestBody request: UpdateBoxRequest,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<BoxResponse> {
        val box = boxService.updateBox(
            id = id,
            title = request.title,
            price = request.price,
            quantity = request.quantity,
            image = request.image
        )

        return ResponseEntity.ok(box)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a box", description = "Delete a box by its ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Box successfully deleted",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Map::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Box not found")
    )
    fun deleteBox(
        @Parameter(description = "ID of the box to delete", required = true)
        @PathVariable id: String,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Map<String, Boolean>> {
        boxService.deleteBox(id)
        return ResponseEntity.ok(mapOf("success" to true))
    }

    @PatchMapping("/{id}/quantity")
    @Operation(summary = "Update box quantity", description = "Update the quantity of an existing box")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Quantity successfully updated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = BoxResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid quantity"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Box not found")
    )
    fun updateQuantity(
        @Parameter(description = "ID of the box to update quantity", required = true)
        @PathVariable id: String,

        @Parameter(description = "New quantity value", required = true)
        @RequestBody request: UpdateQuantityRequest,

        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<BoxResponse> {
        val box = boxService.updateQuantity(id, request.quantity)
        return ResponseEntity.ok(box)
    }
}

package com.wyzly.wyzlybackend.controller

import com.wyzly.wyzlybackend.controller.dto.LoginRequest
import com.wyzly.wyzlybackend.controller.dto.LoginResponse
import com.wyzly.wyzlybackend.controller.dto.RegisterRequest
import com.wyzly.wyzlybackend.controller.dto.UserResponse
import com.wyzly.wyzlybackend.application.AuthService
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
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
@Tag(name = "Authentication", description = "Authentication and user management APIs")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate a user and return a token")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully authenticated",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = LoginResponse::class))]),
        ApiResponse(responseCode = "401", description = "Invalid credentials")
    )
    fun login(
        @Parameter(description = "Login credentials", required = true)
        @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {
        val response = authService.login(request.email, request.password)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user and return a token")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully registered",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = LoginResponse::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input or email already in use")
    )
    fun register(
        @Parameter(description = "Registration details", required = true)
        @RequestBody request: RegisterRequest
    ): ResponseEntity<LoginResponse> {
        val response = authService.register(request.email, request.password, request.name, request.role)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Invalidate the user's token")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully logged out",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Map::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    )
    fun logout(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<Map<String, Boolean>> {
        authService.logout(token.replace("Bearer ", ""))
        return ResponseEntity.ok(mapOf("success" to true))
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get the current authenticated user's details")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Successfully retrieved user details",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = UserResponse::class))]),
        ApiResponse(responseCode = "401", description = "Unauthorized")
    )
    fun getCurrentUser(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<UserResponse> {
        val user = authService.getCurrentUser(token.replace("Bearer ", ""))
        return ResponseEntity.ok(user)
    }
}

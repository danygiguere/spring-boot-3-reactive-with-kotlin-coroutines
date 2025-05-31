package com.example.demo.app.auth

import com.example.demo.app.auth.requests.LoginRequest
import com.example.demo.security.Tokenizer
import com.example.demo.app.user.UserService
import com.example.demo.app.user.dtos.UserDto
import com.example.demo.app.auth.requests.RegisterRequest
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val userService: UserService,
                     private val tokenizer: Tokenizer,
                     private val passwordEncoder: PasswordEncoder) {

    companion object: KLogging()

    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<String> {
        if (userService.findByEmail(request.email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.")
        }
        val newUser = userService.register(request)
        return if (newUser != null) {
            ResponseEntity.ok().body("An email will be sent to you. Please confirm your email by clicking the verification link provided in the email")
        } else {
            logger.error("Error while registering user with email: ${request.email}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping("/login")
    suspend fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserDto> {
        val user = userService.findByEmail(request.email)
        val passwordMatch = passwordEncoder.matches(request.password, user?.password)
        return if (user != null && passwordMatch) {
            ResponseEntity.ok().header("Authorization", tokenizer.createBearerToken(user.id)).body(user)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}
package com.example.demo.app.auth.requests

import com.example.demo.app.user.UserEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 25, message = "{username.size}")
    val username: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Email(message = "{email}")
    val email: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 8, max = 25, message = "{password.size}")
    val password: String
)

fun RegisterRequest.toUserEntity(): UserEntity = UserEntity(
    username = username,
    email = email,
    password = password,
    createdAt = null,
    updatedAt = null
)
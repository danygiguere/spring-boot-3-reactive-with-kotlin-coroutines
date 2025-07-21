package com.example.demo.feature.auth.requests

import com.example.demo.feature.user.UserEntity
import com.example.demo.validators.FieldsMatch
import com.example.demo.validators.UniqueEmail
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@FieldsMatch(first = "password", second = "password_confirmation", message = "{password_must_match}")
data class RegisterRequest(
    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 25, message = "{username.size}")
    val username: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Email(message = "{email}")
    @get:UniqueEmail
    val email: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 8, max = 25, message = "{password.size}")
    val password: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 8, max = 25, message = "{password.size}")
    val password_confirmation: String
)

fun RegisterRequest.toUserEntity(): UserEntity = UserEntity(
    username = username,
    email = email,
    password = password,
    createdAt = null,
    updatedAt = null
)
package com.example.demo.user.requests

import com.example.demo.user.UserEntity
import com.example.demo.validators.IsValidPhoneNumber
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUserRequest(
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
    @get:IsValidPhoneNumber
    val phoneNumber: String,
)

fun CreateUserRequest.toUserEntity(): UserEntity = UserEntity(
    username = username,
    email = email,
    phoneNumber = phoneNumber,
    createdAt = null,
    updatedAt = null
)
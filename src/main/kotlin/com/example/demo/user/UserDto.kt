package com.example.demo.user

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserDto(
        val id: Long?,

        @get:NotNull()
        @get:NotEmpty()
        @get:Size(min = 6, max = 25, message = "{username.size}")
        val username: String,

        @get:NotNull()
        @get:NotEmpty()
        @get:Size(min = 6, max = 25, message = "{email.size}")
        val email: String,

        @get:NotNull()
        @get:NotEmpty()
        @get:Size(min = 6, max = 25, message = "{password.size}")
        val password: String
)

fun UserDto.toEntity(): User = User(
        username = username,
        email = email,
        password = password
)
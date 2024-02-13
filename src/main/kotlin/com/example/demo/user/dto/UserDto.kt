package com.example.demo.user.dto

import com.example.demo.user.UserEntity
import com.example.demo.validator.IsValidPhoneNumber
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
        @get:IsValidPhoneNumber
        val phoneNumber: String,
)

fun UserDto.toEntity(): UserEntity = UserEntity(
        username = username,
        email = email,
        phoneNumber = phoneNumber
)

fun UserDto.toUserWithPostsDto(): UserWithPostsDto = UserWithPostsDto(
        id = id,
        username = username,
        email = email,
        phoneNumber = phoneNumber
)
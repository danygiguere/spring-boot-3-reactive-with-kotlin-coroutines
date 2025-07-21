package com.example.demo.feature.user.dtos

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

data class UserDto(
        val id: Long?,
        val username: String,
        val email: String,
        @JsonIgnore()
        val password: String?,
        var createdAt: LocalDateTime?,
        var updatedAt: LocalDateTime?
)

fun UserDto.toUserWithPostsDto(): UserWithPostsDto = UserWithPostsDto(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
)

fun UserDto.toUserWithImagesDto(): UserWithImagesDto = UserWithImagesDto(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
)
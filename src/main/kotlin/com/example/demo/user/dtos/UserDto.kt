package com.example.demo.user.dtos

import com.example.demo.user.UserEntity
import java.time.LocalDateTime

data class UserDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var createdAt: LocalDateTime?,
        var updatedAt: LocalDateTime?
)

fun UserDto.toUserEntity(): UserEntity = UserEntity(
        username = username,
        email = email,
        phoneNumber = phoneNumber,
        createdAt = createdAt,
        updatedAt = updatedAt
)

fun UserDto.toUserWithPostsDto(): UserWithPostsDto = UserWithPostsDto(
        id = id,
        username = username,
        email = email,
        phoneNumber = phoneNumber,
        createdAt = createdAt,
        updatedAt = updatedAt
)

fun UserDto.toUserWithImagesDto(): UserWithImagesDto = UserWithImagesDto(
        id = id,
        username = username,
        email = email,
        phoneNumber = phoneNumber,
        createdAt = createdAt,
        updatedAt = updatedAt
)
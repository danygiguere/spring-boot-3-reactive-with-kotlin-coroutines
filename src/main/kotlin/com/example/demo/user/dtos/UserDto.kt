package com.example.demo.user.dtos

import com.example.demo.user.UserEntity

data class UserDto(
        val id: Long?,
        val username: String,
        val email: String,
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

fun UserDto.toUserWithImagesDto(): UserWithImagesDto = UserWithImagesDto(
        id = id,
        username = username,
        email = email,
        phoneNumber = phoneNumber
)
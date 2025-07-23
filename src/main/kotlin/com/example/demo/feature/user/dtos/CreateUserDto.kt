package com.example.demo.feature.user.dtos

import com.example.demo.feature.auth.requests.RegisterRequest
import com.example.demo.feature.user.UserEntity

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String) {
}

fun CreateUserDto.toUserEntity(): UserEntity = UserEntity(
    username = username,
    email = email,
    password = password,
    createdAt = null,
    updatedAt = null
)
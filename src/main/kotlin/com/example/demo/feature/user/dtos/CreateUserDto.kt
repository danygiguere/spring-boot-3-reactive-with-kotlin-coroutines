package com.example.demo.feature.user.dtos

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String) {
}
package com.example.demo.user
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class User(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    val password: String
)

fun User.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email,
    password = password
)
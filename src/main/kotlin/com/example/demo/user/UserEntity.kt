package com.example.demo.user
import com.example.demo.user.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class UserEntity(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    val phoneNumber: String
)

fun UserEntity.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email,
    phoneNumber = phoneNumber
)
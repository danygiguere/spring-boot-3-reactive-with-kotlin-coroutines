package com.example.demo.user
import com.example.demo.user.dtos.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class UserEntity(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun UserEntity.toUserDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email,
    createdAt = createdAt,
    updatedAt = updatedAt
)
package com.example.demo.app.user
import com.example.demo.app.user.dtos.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class UserEntity(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    @JsonIgnore()
    val password: String? = null,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun UserEntity.toUserDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email,
    password = password,
    createdAt = createdAt,
    updatedAt = updatedAt
)
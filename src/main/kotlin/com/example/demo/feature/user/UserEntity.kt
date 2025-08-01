package com.example.demo.feature.user
import com.example.demo.feature.user.dtos.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import io.r2dbc.spi.Row
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.ZonedDateTime

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

fun Row.toUserEntity(): UserEntity = UserEntity(
    id = get("id") as Long,
    username = get("username") as String,
    email = get("email") as String,
    password = get("password") as String,
    createdAt = (get("createdAt") as ZonedDateTime).toLocalDateTime(),
    updatedAt = (get("updatedAt") as ZonedDateTime).toLocalDateTime()
)


fun UserEntity.toUserDto(): UserDto = UserDto(
    id = id ?: throw IllegalStateException("UserEntity id cannot be null when converting to UserDto"),
    username = username,
    email = email,
    password = password,
    createdAt = createdAt ?: throw IllegalStateException("UserEntity createdAt cannot be null when converting to UserDto"),
    updatedAt = updatedAt ?: throw IllegalStateException("UserEntity updatedAt cannot be null when converting to UserDto")
)
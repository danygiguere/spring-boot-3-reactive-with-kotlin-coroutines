package com.example.demo.app.post.dtos

import com.example.demo.app.user.dtos.UserDto
import java.time.LocalDateTime

data class PostWithUserDto(
    val id: Long,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var user: UserDto? = null
)
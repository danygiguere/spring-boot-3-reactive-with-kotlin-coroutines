package com.example.demo.app.post.dtos

import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

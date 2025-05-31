package com.example.demo.app.user.dtos

import com.example.demo.app.post.dtos.PostDto
import java.time.LocalDateTime

data class UserWithPostsDto(
        val id: Long?,
        val username: String,
        val email: String,
        var createdAt: LocalDateTime?,
        var updatedAt: LocalDateTime?,
        var posts: List<PostDto>? = emptyList()
)
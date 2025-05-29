package com.example.demo.user.dtos

import com.example.demo.post.dtos.PostDto
import java.time.LocalDateTime

data class UserWithPostsDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var createdAt: LocalDateTime?,
        var updatedAt: LocalDateTime?,
        var posts: List<PostDto>? = emptyList()
)
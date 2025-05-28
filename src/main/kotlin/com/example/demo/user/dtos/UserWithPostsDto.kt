package com.example.demo.user.dtos

import com.example.demo.post.dtos.PostDto

data class UserWithPostsDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var posts: List<PostDto>? = emptyList()
)
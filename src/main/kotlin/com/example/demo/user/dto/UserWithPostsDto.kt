package com.example.demo.user.dto

import com.example.demo.post.PostDto

data class UserWithPostsDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var posts: List<PostDto>? = emptyList()
)
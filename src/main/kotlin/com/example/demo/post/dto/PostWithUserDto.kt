package com.example.demo.post.dto

import com.example.demo.user.dto.UserDto

data class PostWithUserDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
    var user: UserDto? = null
)
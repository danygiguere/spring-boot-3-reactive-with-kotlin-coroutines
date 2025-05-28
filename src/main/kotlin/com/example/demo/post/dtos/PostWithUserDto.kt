package com.example.demo.post.dtos

import com.example.demo.user.dtos.UserDto

data class PostWithUserDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
    var user: UserDto? = null
)
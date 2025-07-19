package com.example.demo.app.post.dtos

data class CreatePostDto(
    var userId: Long,
    val title: String,
    val description: String
)
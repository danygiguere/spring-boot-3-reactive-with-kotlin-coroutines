package com.example.demo.feature.post.dtos

data class CreatePostDto(
    var userId: Long,
    val title: String,
    val description: String
)

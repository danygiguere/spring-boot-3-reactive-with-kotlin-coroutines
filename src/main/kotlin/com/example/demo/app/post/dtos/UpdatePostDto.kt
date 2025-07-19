package com.example.demo.app.post.dtos

data class UpdatePostDto(
    var id: Long,
    var userId: Long,
    val title: String,
    val description: String
)
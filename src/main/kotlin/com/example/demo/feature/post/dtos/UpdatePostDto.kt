package com.example.demo.feature.post.dtos

data class UpdatePostDto(
    var id: Long,
    var userId: Long,
    val title: String,
    val description: String
)
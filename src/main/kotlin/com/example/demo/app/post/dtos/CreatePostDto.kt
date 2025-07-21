package com.example.demo.app.post.dtos

import com.example.demo.app.post.PostEntity

data class CreatePostDto(
    var userId: Long,
    val title: String,
    val description: String
)

fun CreatePostDto.toEntity(): PostEntity = PostEntity(
    id = null,
    userId = userId,
    title = title,
    description = description,
    createdAt = null,
    updatedAt = null
)

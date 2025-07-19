package com.example.demo.app.post.dtos

import com.example.demo.app.post.PostEntity

data class UpdatePostDto(
    var id: Long,
    var userId: Long,
    val title: String,
    val description: String
)

fun UpdatePostDto.toEntity(): PostEntity = PostEntity(
    id = null,
    userId = userId,
    title = title,
    description = description,
    createdAt = null,
    updatedAt = null
)
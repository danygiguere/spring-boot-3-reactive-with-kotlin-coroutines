package com.example.demo.feature.post.dtos

import com.example.demo.feature.post.PostEntity
import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)

fun PostDto.toPostWithImagesDto(): PostWithImagesDto = PostWithImagesDto(
    id = id,
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun PostDto.toPostWithUserDto(): PostWithUserDto = PostWithUserDto(
    id = id,
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)
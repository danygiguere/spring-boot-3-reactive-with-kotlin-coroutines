package com.example.demo.post.dtos

import com.example.demo.post.PostEntity
import java.time.LocalDateTime

data class PostDto(
    val id: Long,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun PostDto.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
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
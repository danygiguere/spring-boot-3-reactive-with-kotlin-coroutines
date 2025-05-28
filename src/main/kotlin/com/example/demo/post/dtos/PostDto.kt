package com.example.demo.post.dtos

import com.example.demo.post.PostEntity

data class PostDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
)

fun PostDto.toEntity(): PostEntity = PostEntity(
    userId = userId,
    title = title,
    description = description
)

fun PostDto.toPostWithImagesDto(): PostWithImagesDto = PostWithImagesDto(
        id = id,
        userId = userId,
        title = title,
        description = description
)

fun PostDto.toPostWithUserDto(): PostWithUserDto = PostWithUserDto(
        id = id,
        userId = userId,
        title = title,
        description = description
)
package com.example.demo.post.dto

import com.example.demo.post.PostEntity
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PostDto(
    val id: Long?,

    var userId: Long,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 25, message = "{title.size}")
    val title: String,

    @get:NotEmpty()
    @get:Size(min = 6, max = 25, message = "{description.size}")
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
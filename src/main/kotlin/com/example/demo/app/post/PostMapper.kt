package com.example.demo.app.post

import com.example.demo.app.post.dtos.CreatePostDto
import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.dtos.PostWithImagesDto
import com.example.demo.app.post.dtos.PostWithUserDto
import com.example.demo.app.post.dtos.UpdatePostDto
import io.r2dbc.spi.Row
import java.time.ZonedDateTime


fun Row.toPostEntity(): PostEntity = PostEntity(
    id = get("id") as Long,
    userId = get("userId") as Long,
    title = get("title") as String,
    description = get("description") as String,
    createdAt = (get("createdAt") as ZonedDateTime).toLocalDateTime(),
    updatedAt = (get("updatedAt") as ZonedDateTime).toLocalDateTime()
)

fun PostEntity.toPostDto(): PostDto = PostDto(
    id = id ?: throw IllegalStateException("PostEntity id cannot be null when converting to PostDto"),
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt ?: throw IllegalStateException("PostEntity createdAt cannot be null when converting to PostDto"),
    updatedAt = updatedAt ?: throw IllegalStateException("PostEntity updatedAt cannot be null when converting to PostDto")
)

fun List<PostEntity>.toPostDtos(): List<PostDto> = this.map { it.toPostDto() }

fun PostDto.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun CreatePostDto.toEntity(): PostEntity = PostEntity(
    id = null,
    userId = userId,
    title = title,
    description = description,
    createdAt = null,
    updatedAt = null
)

fun UpdatePostDto.toEntity(): PostEntity = PostEntity(
    id = null,
    userId = userId,
    title = title,
    description = description,
    createdAt = null,
    updatedAt = null
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
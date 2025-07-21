package com.example.demo.app.image

import com.example.demo.app.image.dtos.ImageDto
import io.r2dbc.spi.Row
import java.time.ZonedDateTime

fun Row.toImageEntity(): ImageEntity = ImageEntity(
    id = get("id") as Long,
    postId = get("postId") as Long,
    url = get("url") as String,
    createdAt = (get("createdAt") as ZonedDateTime).toLocalDateTime(),
    updatedAt = (get("updatedAt") as ZonedDateTime).toLocalDateTime()
)

fun List<ImageEntity>.toImageDtos(): List<ImageDto> = this.map { it.toImageDto() }


fun ImageEntity.toImageDto(): ImageDto = ImageDto(
    id = id ?: throw IllegalStateException("PostEntity id cannot be null when converting to PostDto"),
    postId = postId,
    url = url,
    createdAt = createdAt ?: throw IllegalStateException("PostEntity createdAt cannot be null when converting to PostDto"),
    updatedAt = updatedAt ?: throw IllegalStateException("PostEntity updatedAt cannot be null when converting to PostDto")
)
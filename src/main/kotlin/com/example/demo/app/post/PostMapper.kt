package com.example.demo.app.post

import com.example.demo.app.post.dtos.CreatePostDto
import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.dtos.PostWithImagesDto
import com.example.demo.app.post.dtos.PostWithUserDto
import com.example.demo.app.post.dtos.UpdatePostDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.function.BiFunction

@Component
class PostMapper: BiFunction<Row, Any, PostDto> {
    override fun apply(row: Row, o: Any): PostDto {
        return PostEntity(
                row.get("id") as Long,
                row.get("userId") as Long,
                row.get("title") as String,
                row.get("description") as String,
            (row.get("createdAt") as ZonedDateTime).toLocalDateTime(),
            (row.get("updatedAt") as ZonedDateTime).toLocalDateTime(),
        ).toDto()
    }
}

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

fun PostEntity.toDto(): PostDto = PostDto(
    id = id ?: throw IllegalStateException("PostEntity id cannot be null when converting to PostDto"),
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)
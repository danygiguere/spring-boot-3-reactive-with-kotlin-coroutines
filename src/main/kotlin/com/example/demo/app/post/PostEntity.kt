package com.example.demo.app.post
import com.example.demo.app.post.dtos.PostDto
import io.r2dbc.spi.Row
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Table
data class PostEntity(
    @Id var id: Long? = null,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)


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
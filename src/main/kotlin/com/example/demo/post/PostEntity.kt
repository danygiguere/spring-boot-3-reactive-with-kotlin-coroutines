package com.example.demo.post
import com.example.demo.post.dtos.PostDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class PostEntity(
    @Id var id: Long? = null,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun PostEntity.toDto(): PostDto = PostDto(
    id = id,
    userId = userId,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt
)
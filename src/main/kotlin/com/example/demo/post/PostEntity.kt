package com.example.demo.post
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Post(
    @Id var id: Long? = null,
    var userId: Long,
    val title: String,
    val description: String,
)

fun Post.toDto(): PostDto = PostDto(
    id = id,
    userId = userId,
    title = title,
    description = description,
)
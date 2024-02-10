package com.example.demo.post
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Post(
    @Id var id: Long? = null,
    val title: String,
    val description: String,
)

fun Post.toDto(): PostDto = PostDto(
    id = id,
    title = title,
    description = description,
)
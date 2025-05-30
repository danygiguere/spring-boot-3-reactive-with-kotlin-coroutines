package com.example.demo.post.requests

import com.example.demo.post.PostEntity
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreatePostRequest(
    var userId: Long,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 255, message = "{title.size}")
    val title: String,

    @get:NotEmpty()
    @get:Size(min = 6, max = 1000, message = "{description.size}")
    val description: String,
)

fun CreatePostRequest.toEntity(): PostEntity = PostEntity(
    id = null,
    userId = userId,
    title = title,
    description = description,
    createdAt = null,
    updatedAt = null
)
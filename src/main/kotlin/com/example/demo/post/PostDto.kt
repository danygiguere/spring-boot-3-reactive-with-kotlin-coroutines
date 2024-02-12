package com.example.demo.post

import com.example.demo.image.ImageDto
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

    var images: List<ImageDto>? = emptyList()
)

fun PostDto.toEntity(): Post = Post(
    userId = userId,
    title = title,
    description = description
)
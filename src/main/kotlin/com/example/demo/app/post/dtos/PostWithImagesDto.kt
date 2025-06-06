package com.example.demo.app.post.dtos

import com.example.demo.app.image.dtos.ImageDto
import java.time.LocalDateTime

data class PostWithImagesDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?,
    var images: List<ImageDto>? = emptyList()
)
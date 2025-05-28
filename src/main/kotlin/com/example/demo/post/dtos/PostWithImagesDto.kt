package com.example.demo.post.dtos

import com.example.demo.image.dtos.ImageDto

data class PostWithImagesDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
    var images: List<ImageDto>? = emptyList()
)
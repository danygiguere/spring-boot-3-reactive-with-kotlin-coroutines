package com.example.demo.post.dto

import com.example.demo.image.ImageDto

data class PostWithImagesDto(
    val id: Long?,
    var userId: Long,
    val title: String,
    val description: String,
    var images: List<ImageDto>? = emptyList()
)
package com.example.demo.feature.image.dtos

import java.time.LocalDateTime

data class ImageDto(
    val id: Long,
    var postId: Long,
    val url: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
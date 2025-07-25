package com.example.demo.feature.user.dtos

import com.example.demo.feature.image.dtos.ImageDto
import java.time.LocalDateTime

data class UserWithImagesDto(
        val id: Long,
        val username: String,
        val email: String,
        var createdAt: LocalDateTime,
        var updatedAt: LocalDateTime,
        var images: List<ImageDto>? = emptyList()
)
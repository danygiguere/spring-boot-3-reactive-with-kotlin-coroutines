package com.example.demo.user.dtos

import com.example.demo.image.dtos.ImageDto
import java.time.LocalDateTime

data class UserWithImagesDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var createdAt: LocalDateTime?,
        var updatedAt: LocalDateTime?,
        var images: List<ImageDto>? = emptyList()
)
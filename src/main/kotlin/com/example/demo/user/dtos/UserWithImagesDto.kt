package com.example.demo.user.dtos

import com.example.demo.image.dtos.ImageDto

data class UserWithImagesDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var images: List<ImageDto>? = emptyList()
)
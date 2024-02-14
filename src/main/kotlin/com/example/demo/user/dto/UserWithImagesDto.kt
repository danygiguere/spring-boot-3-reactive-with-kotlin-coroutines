package com.example.demo.user.dto

import com.example.demo.image.ImageDto

data class UserWithImagesDto(
        val id: Long?,
        val username: String,
        val email: String,
        val phoneNumber: String,
        var images: List<ImageDto>? = emptyList()
)
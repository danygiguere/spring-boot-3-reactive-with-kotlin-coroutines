package com.example.demo.image.dtos

import com.example.demo.image.ImageEntity
import java.time.LocalDateTime

data class ImageDto(
    val id: Long?,
    var postId: Long,
    val url: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun ImageDto.toEntity(): ImageEntity = ImageEntity(
    postId = postId,
    url = url
)
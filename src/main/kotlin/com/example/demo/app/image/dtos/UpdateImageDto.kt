package com.example.demo.app.image.dtos

import com.example.demo.app.image.ImageEntity
import java.time.LocalDateTime

data class UpdateImageDto(
    val id: Long?,
    var postId: Long,
    val url: String,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
)

fun UpdateImageDto.toEntity(): ImageEntity = ImageEntity(
    postId = postId,
    url = url
)
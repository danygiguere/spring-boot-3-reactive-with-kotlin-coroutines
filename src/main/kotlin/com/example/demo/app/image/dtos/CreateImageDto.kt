package com.example.demo.app.image.dtos

import com.example.demo.app.image.ImageEntity

data class CreateImageDto(
    var postId: Long,
    val url: String,
)

fun CreateImageDto.toEntity(): ImageEntity = ImageEntity(
    postId = postId,
    url = url
)
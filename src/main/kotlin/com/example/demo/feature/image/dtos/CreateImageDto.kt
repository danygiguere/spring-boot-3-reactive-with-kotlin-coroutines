package com.example.demo.feature.image.dtos

import com.example.demo.feature.image.ImageEntity

data class CreateImageDto(
    var postId: Long,
    val url: String,
)

fun CreateImageDto.toEntity(): ImageEntity = ImageEntity(
    postId = postId,
    url = url
)
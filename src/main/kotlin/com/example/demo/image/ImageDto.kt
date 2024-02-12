package com.example.demo.image

data class ImageDto(
    val id: Long?,

    var postId: Long,

    val url: String
)

fun ImageDto.toEntity(): Image = Image(
    postId = postId,
    url = url
)
package com.example.demo.image
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Image(
    @Id var id: Long? = null,
    var postId: Long,
    val url: String
)

fun Image.toDto(): ImageDto = ImageDto(
    id = id,
    postId = postId,
    url = url
)
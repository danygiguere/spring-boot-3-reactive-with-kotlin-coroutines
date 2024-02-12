package com.example.demo.image
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class ImageEntity(
    @Id var id: Long? = null,
    var postId: Long,
    val url: String
)

fun ImageEntity.toDto(): ImageDto = ImageDto(
    id = id,
    postId = postId,
    url = url
)
package com.example.demo.app.image
import com.example.demo.app.image.dtos.ImageDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class ImageEntity(
    @Id var id: Long? = null,
    var postId: Long,
    val url: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)

//fun ImageEntity.toDto(): ImageDto = ImageDto(
//    id = id,
//    postId = postId,
//    url = url,
//    createdAt = createdAt,
//    updatedAt = updatedAt
//)

fun List<ImageEntity>.toImageDtos(): List<ImageDto> = this.map { it.toImageDto() }
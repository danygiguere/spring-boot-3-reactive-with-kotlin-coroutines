package com.example.demo.feature.image
import com.example.demo.feature.image.dtos.ImageDto
import io.r2dbc.spi.Row
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Table
data class ImageEntity(
    @Id var id: Long? = null,
    var postId: Long,
    val url: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)

fun Row.toImageEntity(): ImageEntity = ImageEntity(
    id = get("id") as Long,
    postId = get("postId") as Long,
    url = get("url") as String,
    createdAt = (get("createdAt") as ZonedDateTime).toLocalDateTime(),
    updatedAt = (get("updatedAt") as ZonedDateTime).toLocalDateTime()
)

fun List<ImageEntity>.toImageDtos(): List<ImageDto> = this.map { it.toImageDto() }


fun ImageEntity.toImageDto(): ImageDto = ImageDto(
    id = id ?: throw IllegalStateException("ImageEntity id cannot be null when converting to ImageDto"),
    postId = postId,
    url = url,
    createdAt = createdAt ?: throw IllegalStateException("ImageEntity createdAt cannot be null when converting to ImageDto"),
    updatedAt = updatedAt ?: throw IllegalStateException("ImageEntity updatedAt cannot be null when converting to ImageDto")
)


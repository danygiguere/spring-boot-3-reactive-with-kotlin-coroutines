package com.example.demo.app.image

import com.example.demo.app.image.dtos.ImageDto
import com.example.demo.app.post.PostEntity
import com.example.demo.app.post.dtos.PostDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.function.BiFunction

@Component
class ImageMapper: BiFunction<Row, Any, ImageDto> {
    override fun apply(row: Row, o: Any): ImageDto {
        return ImageEntity(
                row.get("id") as Long,
                row.get("postId") as Long,
                row.get("url") as String,
            (row.get("createdAt") as ZonedDateTime).toLocalDateTime(),
            (row.get("updatedAt") as ZonedDateTime).toLocalDateTime(),
        ).toDto()
    }
}

fun Row.toImageEntity(): ImageEntity = ImageEntity(
    id = get("id") as Long,
    postId = get("userId") as Long,
    url = get("title") as String,
    createdAt = (get("createdAt") as ZonedDateTime).toLocalDateTime(),
    updatedAt = (get("updatedAt") as ZonedDateTime).toLocalDateTime()
)


fun ImageEntity.toImageDto(): ImageDto = ImageDto(
    id = id ?: throw IllegalStateException("PostEntity id cannot be null when converting to PostDto"),
    postId = postId,
    url = url,
    createdAt = createdAt ?: throw IllegalStateException("PostEntity createdAt cannot be null when converting to PostDto"),
    updatedAt = updatedAt ?: throw IllegalStateException("PostEntity updatedAt cannot be null when converting to PostDto")
)
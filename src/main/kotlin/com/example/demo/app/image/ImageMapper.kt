package com.example.demo.app.image

import com.example.demo.app.image.dtos.ImageDto
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
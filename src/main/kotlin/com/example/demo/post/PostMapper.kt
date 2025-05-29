package com.example.demo.post

import com.example.demo.post.dtos.PostDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.function.BiFunction

@Component
class PostMapper: BiFunction<Row, Any, PostDto> {
    override fun apply(row: Row, o: Any): PostDto {
        return PostEntity(
                row.get("id") as Long,
                row.get("userId") as Long,
                row.get("title") as String,
                row.get("description") as String,
            (row.get("createdAt") as LocalDateTime),
            (row.get("updatedAt") as LocalDateTime)
        ).toDto()
    }
}
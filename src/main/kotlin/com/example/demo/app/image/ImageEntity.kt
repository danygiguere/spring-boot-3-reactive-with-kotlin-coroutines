package com.example.demo.app.image
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


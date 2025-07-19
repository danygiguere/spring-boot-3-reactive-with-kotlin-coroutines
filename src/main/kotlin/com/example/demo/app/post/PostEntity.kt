package com.example.demo.app.post
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class PostEntity(
    @Id var id: Long? = null,
    var userId: Long,
    val title: String,
    val description: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)
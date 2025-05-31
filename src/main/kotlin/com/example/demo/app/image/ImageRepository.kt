package com.example.demo.app.image

import com.example.demo.app.image.dtos.ImageDto
import com.example.demo.app.image.dtos.toEntity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class ImageRepository(private val databaseClient: DatabaseClient,
                      private val imageMapper: ImageMapper) {

    suspend fun findAll(): List<ImageDto>? =
            databaseClient.sql("SELECT * FROM images")
                    .map(imageMapper::apply)
                    .flow().toList()

    suspend fun findById(id: Long): ImageDto? =
            databaseClient.sql("SELECT * FROM images WHERE id = :id")
                    .bind("id", id)
                    .map(imageMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByPostId(postId: Long): List<ImageDto>? =
            databaseClient.sql("SELECT * FROM images WHERE postId = :postId")
                    .bind("postId", postId)
                    .map(imageMapper::apply)
                    .flow().toList()

    suspend fun findByUserIdThroughPosts(userId: Long): List<ImageDto>? =
            databaseClient.sql("""
                SELECT images.* FROM images 
                INNER JOIN posts 
                ON posts.id = images.postId 
                WHERE posts.userId IN (:userId);
            """.trimIndent())
                    .bind("userId", userId)
                    .map(imageMapper::apply)
                    .flow().toList()

    suspend fun create(imageDto: ImageDto): ImageDto =
            databaseClient.sql("INSERT INTO images (postId, url) VALUES (:postId, :url)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("postId", imageDto.postId)
                    .bind("url", imageDto.url)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val postEntity = imageDto.toEntity().copy(id = id)
                        postEntity.toDto()
                    }
                    .awaitSingle()

    suspend fun update(id: Long, imageDto: ImageDto): Long =
            databaseClient.sql("UPDATE images SET url = :url WHERE id = :id")
                    .bind("id", id)
                    .bind("url", imageDto.url)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM images WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
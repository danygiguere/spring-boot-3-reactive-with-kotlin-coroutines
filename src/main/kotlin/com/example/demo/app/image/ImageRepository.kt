package com.example.demo.app.image

import com.example.demo.app.image.dtos.CreateImageDto
import com.example.demo.app.image.dtos.ImageDto
import com.example.demo.app.image.dtos.UpdateImageDto
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class ImageRepository(private val databaseClient: DatabaseClient) {

    suspend fun findAll(): List<ImageEntity>? =
            databaseClient.sql("SELECT * FROM images")
                     .map { row, _ -> row.toImageEntity() }
                    .flow().toList()

    suspend fun findById(id: Long): ImageEntity? =
            databaseClient.sql("SELECT * FROM images WHERE id = :id")
                    .bind("id", id)
                    .map { row, _ -> row.toImageEntity() }
                    .awaitOneOrNull()

    suspend fun findByPostId(postId: Long): List<ImageEntity>? =
            databaseClient.sql("SELECT * FROM images WHERE postId = :postId")
                    .bind("postId", postId)
                    .map { row, _ -> row.toImageEntity() }
                    .flow().toList()

    suspend fun findByUserIdThroughPosts(userId: Long): List<ImageEntity>? =
            databaseClient.sql("""
                SELECT images.* FROM images 
                INNER JOIN posts 
                ON posts.id = images.postId 
                WHERE posts.userId IN (:userId);
            """.trimIndent())
                    .bind("userId", userId)
                    .map { row, _ -> row.toImageEntity() }
                    .flow().toList()

    suspend fun create(createImageDto: CreateImageDto): Long =
            databaseClient.sql("INSERT INTO images (postId, url) VALUES (:postId, :url)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("postId", createImageDto.postId)
                    .bind("url", createImageDto.url)
                    .fetch()
                    .first()
                    .map { row -> (row["id"] as Number).toLong() }
                    .awaitSingle()

    suspend fun update(updateImageDto: UpdateImageDto): Long =
            databaseClient.sql("UPDATE images SET url = :url WHERE id = :id")
                    .bind("id", updateImageDto.id)
                    .bind("url", updateImageDto.url)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM images WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
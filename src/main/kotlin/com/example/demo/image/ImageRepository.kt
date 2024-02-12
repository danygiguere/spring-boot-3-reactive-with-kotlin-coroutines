package com.example.demo.image

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Repository

@Repository
class ImageRepository(private val databaseClient: DatabaseClient,
                      private val imageMapper: ImageMapper) {

    suspend fun findAll(): Flow<ImageDto>? =
            databaseClient.sql("SELECT * FROM images")
                    .map(imageMapper::apply)
                    .flow()

    suspend fun findById(id: Long): ImageDto? =
            databaseClient.sql("SELECT * FROM images WHERE id = :id")
                    .bind("id", id)
                    .map(imageMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByPostId(postId: Long): Flow<ImageDto>? =
            databaseClient.sql("SELECT * FROM images WHERE postId = :postId")
                    .bind("postId", postId)
                    .map(imageMapper::apply)
                    .flow()

    suspend fun create(postId: Long, postDto: ImageDto): ImageDto? =
            databaseClient.sql("INSERT INTO images (postId, url) VALUES (:postId, :url)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("postId", postId)
                    .bind("url", postDto.url)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val postEntity = postDto.toEntity().copy(id = id)
                        postEntity.toDto()
                    }
                    .awaitSingleOrNull()

    suspend fun update(id: Long, postDto: ImageDto): Long =
            databaseClient.sql("UPDATE images SET url = :url WHERE id = :id")
                    .bind("id", id)
                    .bind("url", postDto.url)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM images WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
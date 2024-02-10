package com.example.demo.post

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Repository

@Repository
class PostRepository(private val databaseClient: DatabaseClient,
                     private val mapper: PostMapper) {

    suspend fun findAll(): Flow<PostDto>? =
            databaseClient.sql("SELECT * FROM posts")
                    .map(mapper::apply)
                    .flow()

    suspend fun findById(id: Long): PostDto? =
            databaseClient.sql("SELECT * FROM posts WHERE id = :id")
                    .bind("id", id)
                    .map(mapper::apply)
                    .awaitOneOrNull()

    suspend fun create(postDto: PostDto): PostDto? =
            databaseClient.sql("INSERT INTO posts (title, description) VALUES (:title, :description)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("title", postDto.title)
                    .bind("description", postDto.description)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val postEntity = postDto.toEntity().copy(id = id)
                        postEntity.toDto()
                    }
                    .awaitSingleOrNull()

    suspend fun update(id: Long, postDto: PostDto): Long =
            databaseClient.sql("UPDATE posts SET title = :title, description = :description WHERE id = :id")
                    .bind("id", id)
                    .bind("title", postDto.title)
                    .bind("description", postDto.description)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM posts WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
package com.example.demo.post

import com.example.demo.post.dto.PostDto
import com.example.demo.post.dto.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostRepository(private val databaseClient: DatabaseClient,
                     private val postMapper: PostMapper) {

    suspend fun findAll(): Flow<PostDto>? =
            databaseClient.sql("SELECT * FROM posts")
                    .map(postMapper::apply)
                    .flow()

    suspend fun findById(id: Long): PostDto? =
            databaseClient.sql("SELECT * FROM posts WHERE id = :id")
                    .bind("id", id)
                    .map(postMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByUserId(userId: Long): Flow<PostDto>? =
            databaseClient.sql("SELECT * FROM posts WHERE userId = :userId")
                    .bind("userId", userId)
                    .map(postMapper::apply)
                    .flow()

    suspend fun create(postDto: PostDto): PostDto =
            databaseClient.sql("INSERT INTO posts (userId, title, description) VALUES (:userId, :title, :description)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("userId", postDto.userId)
                    .bind("title", postDto.title)
                    .bind("description", postDto.description)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val postEntity = postDto.toEntity().copy(id = id)
                        postEntity.toDto()
                    }
                    .awaitSingle()

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
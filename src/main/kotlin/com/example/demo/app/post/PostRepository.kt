package com.example.demo.app.post

import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.requests.CreatePostRequest
import com.example.demo.app.post.requests.UpdatePostRequest
import com.example.demo.app.post.requests.toEntity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostRepository(private val databaseClient: DatabaseClient,
                     private val postMapper: PostMapper) {

    suspend fun findAll(): List<PostDto>? =
        databaseClient.sql("SELECT * FROM posts")
            .map(postMapper::apply)
            .flow().toList()

    suspend fun findById(id: Long): PostDto? =
            databaseClient.sql("SELECT * FROM posts WHERE id = :id")
                    .bind("id", id)
                    .map(postMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByUserId(userId: Long): List<PostDto>? =
            databaseClient.sql("SELECT * FROM posts WHERE userId = :userId")
                    .bind("userId", userId)
                    .map(postMapper::apply)
                .flow().toList()

    suspend fun create(userId: Long, createPostRequest: CreatePostRequest): PostDto =
            databaseClient.sql("INSERT INTO posts (userId, title, description) VALUES (:userId, :title, :description)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("userId", userId)
                    .bind("title", createPostRequest.title)
                    .bind("description", createPostRequest.description)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val postEntity = createPostRequest.toEntity().copy(id = id)
                        postEntity.toDto()
                    }
                    .awaitSingle()

    suspend fun update(id: Long, updatePostRequest: UpdatePostRequest): Long =
            databaseClient.sql("UPDATE posts SET title = :title, description = :description WHERE id = :id")
                    .bind("id", id)
                    .bind("title", updatePostRequest.title)
                    .bind("description", updatePostRequest.description)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM posts WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
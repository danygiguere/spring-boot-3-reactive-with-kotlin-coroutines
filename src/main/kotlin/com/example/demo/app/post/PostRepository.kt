package com.example.demo.app.post

import com.example.demo.app.post.dtos.CreatePostDto
import com.example.demo.app.post.dtos.UpdatePostDto
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.awaitRowsUpdated
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository

@Repository
class PostRepository(private val databaseClient: DatabaseClient) {

    suspend fun findAll(): List<PostEntity>? =
        databaseClient.sql("SELECT * FROM posts")
            .map { row, _ -> row.toPostEntity() }
            .flow().toList()

    suspend fun findById(id: Long): PostEntity? =
            databaseClient.sql("SELECT * FROM posts WHERE id = :id")
                    .bind("id", id)
                    .map { row, _ -> row.toPostEntity() }
                    .awaitOneOrNull()

    suspend fun findByUserId(userId: Long): List<PostEntity>? =
            databaseClient.sql("SELECT * FROM posts WHERE userId = :userId")
                    .bind("userId", userId)
                    .map { row, _ -> row.toPostEntity() }
                .flow().toList()

    suspend fun create(createPostDto: CreatePostDto): Long =
        databaseClient.sql("INSERT INTO posts (userId, title, description) VALUES (:userId, :title, :description)")
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("userId", createPostDto.userId)
            .bind("title", createPostDto.title)
            .bind("description", createPostDto.description)
            .fetch()
            .first()
            .map { row -> (row["id"] as Number).toLong() }
            .awaitSingle()

    suspend fun update(updatePostDto: UpdatePostDto): Long =
            databaseClient.sql("UPDATE posts SET title = :title, description = :description WHERE id = :id")
                    .bind("id", updatePostDto.id)
                    .bind("title", updatePostDto.title)
                    .bind("description", updatePostDto.description)
                    .fetch().awaitRowsUpdated()

    suspend fun delete(id: Long): Long =
            databaseClient.sql("DELETE FROM posts WHERE id = :id")
                    .bind("id", id)
                    .fetch().awaitRowsUpdated()

}
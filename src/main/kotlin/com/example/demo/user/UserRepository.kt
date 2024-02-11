package com.example.demo.user

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val databaseClient: DatabaseClient,
                     private val mapper: UserMapper) {

    suspend fun findById(id: Long): UserDto? =
            databaseClient.sql("SELECT * FROM users WHERE id = :id")
                    .bind("id", id)
                    .map(mapper::apply)
                    .awaitOneOrNull()

    suspend fun findByIdWithPosts(id: Long): UserDto? =
            databaseClient.sql("""
                SELECT * FROM users 
                JOIN posts
                ON users.id = posts.userId
                WHERE users.id = :id
            """.trimIndent())
                    .bind("id", id)
                    .map(mapper::apply)
                    .awaitOneOrNull()

    suspend fun create(userDto: UserDto): UserDto? =
            databaseClient.sql("INSERT INTO users (username, email, phoneNumber) VALUES (:username, :email, :phoneNumber)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("username", userDto.username)
                    .bind("email", userDto.email)
                    .bind("phoneNumber", userDto.phoneNumber)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val userEntity = userDto.toEntity().copy(id = id)
                        userEntity.toDto()
                    }
                    .awaitSingleOrNull()
}
package com.example.demo.user

import com.example.demo.post.PostDto
import com.example.demo.post.PostMapper
import com.example.demo.post.PostRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.awaitSingleOrNull
import mu.KLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val databaseClient: DatabaseClient,
                     private val postRepository: PostRepository,
                     private val userMapper: UserMapper) {

    companion object: KLogging()

    suspend fun findById(id: Long): UserDto? =
            databaseClient.sql("SELECT * FROM users WHERE id = :id")
                    .bind("id", id)
                    .map(userMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByIdWithPosts(id: Long): UserDto? {
        val user = findById(id)
        val posts = postRepository.findByUserId(id)
        if (posts != null) {
            return user?.let { it ->
                UserDto(
                        it.id,
                        it.username,
                        it.email,
                        it.phoneNumber,
                        posts.map { post ->
                            PostDto(
                                    post.id,
                                    post.userId,
                                    post.title,
                                    post.description
                            )
                        }
                )
            }
        }
        return user
    }

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
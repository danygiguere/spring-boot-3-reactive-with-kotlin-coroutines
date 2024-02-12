package com.example.demo.user

import com.example.demo.post.PostRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
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

    // oneToMany relationship query example
    suspend fun findByIdWithPosts(id: Long): UserDto? = coroutineScope {
        val user = async{findById(id)}
        val posts = async{postRepository.findByUserId(id)?.toList()}
        return@coroutineScope user.await()?.copy(posts = posts.await())
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
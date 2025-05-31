package com.example.demo.user

import com.example.demo.user.dtos.UserDto
import com.example.demo.user.requests.CreateUserRequest
import com.example.demo.user.requests.toUserEntity
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val databaseClient: DatabaseClient,
                     private val userMapper: UserMapper,
                     private val passwordEncoder: PasswordEncoder
) {

    companion object: KLogging()

    suspend fun findById(id: Long): UserDto? =
            databaseClient.sql("SELECT * FROM users WHERE id = :id")
                    .bind("id", id)
                    .map(userMapper::apply)
                    .awaitOneOrNull()

    suspend fun create(createUserRequest: CreateUserRequest): UserDto =
            databaseClient.sql("INSERT INTO users (username, email, password) VALUES (:username, :email, :password)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("username", createUserRequest.username)
                    .bind("email", createUserRequest.email)
                    .bind("password", passwordEncoder.encode(createUserRequest.password))
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val userEntity = createUserRequest.toUserEntity().copy(id = id)
                        userEntity.toUserDto()
                    }
                    .awaitSingle()
}
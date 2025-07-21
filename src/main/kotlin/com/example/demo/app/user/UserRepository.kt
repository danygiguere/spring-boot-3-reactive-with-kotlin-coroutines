package com.example.demo.app.user

import com.example.demo.app.user.dtos.UserDto
import com.example.demo.app.auth.requests.RegisterRequest
import com.example.demo.app.auth.requests.toUserEntity
import com.example.demo.app.post.toPostEntity
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

    suspend fun findById(id: Long): UserEntity? =
            databaseClient.sql("SELECT * FROM users WHERE id = :id")
                    .bind("id", id)
                    .map { row, _ -> row.toUserEntity() }
                    .awaitOneOrNull()

    suspend fun findByEmail(email: String): UserEntity? =
        databaseClient.sql("SELECT * FROM users WHERE email = :email")
            .bind("email", email)
            .map { row, _ -> row.toUserEntity() }
            .awaitOneOrNull()

    suspend fun register(registerRequest: RegisterRequest): UserDto =
            databaseClient.sql("INSERT INTO users (username, email, password) VALUES (:username, :email, :password)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("username", registerRequest.username)
                    .bind("email", registerRequest.email)
                    .bind("password", passwordEncoder.encode(registerRequest.password))
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val userEntity = registerRequest.toUserEntity().copy(id = id)
                        userEntity.toUserDto()
                    }
                    .awaitSingle()

}
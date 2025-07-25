package com.example.demo.feature.user

import com.example.demo.feature.user.dtos.CreateUserDto
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val databaseClient: DatabaseClient,
    private val passwordEncoder: PasswordEncoder
) {

    companion object : KLogging()

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

    suspend fun create(createUserDto: CreateUserDto): Long =
        databaseClient.sql("INSERT INTO users (username, email, password) VALUES (:username, :email, :password)")
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("username", createUserDto.username)
            .bind("email", createUserDto.email)
            .bind("password", passwordEncoder.encode(createUserDto.password))
            .fetch()
            .first()
            .map { row -> (row["id"] as Number).toLong() }
            .awaitSingle()

}
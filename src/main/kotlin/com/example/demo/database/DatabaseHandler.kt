package com.example.demo.database

import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class DatabaseHandler(private val databaseRepository: DatabaseRepository) {

    companion object: KLogging()

    suspend fun reCreate() {
        databaseRepository.dropUsersTable()
        databaseRepository.createUsersTable()
        databaseRepository.dropPostsTable()
        databaseRepository.createPostsTable()
        databaseRepository.dropImagesTable()
        databaseRepository.createImagesTable()
    }

    suspend fun reCreateDb(req: ServerRequest): ServerResponse {
        reCreate()
        return ServerResponse.noContent().buildAndAwait()
    }
}
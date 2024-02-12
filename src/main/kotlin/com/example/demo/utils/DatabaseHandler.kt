package com.example.demo.utils


import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class DatabaseHandler(private val databaseRepository: DatabaseRepository) {

    companion object: KLogging()

    suspend fun reCreate(req: ServerRequest): ServerResponse {
        databaseRepository.dropUsersTable()
        databaseRepository.createUsersTable()
        databaseRepository.dropPostsTable()
        databaseRepository.createPostsTable()
        databaseRepository.dropImagesTable()
        databaseRepository.createImagesTable()
        return ServerResponse.noContent().buildAndAwait()
    }
}

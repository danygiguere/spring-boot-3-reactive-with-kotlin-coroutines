package com.example.demo.handler

import com.example.demo.seeders.DataSeeder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

// for demo purposes only
@Component
class DatabaseHandler(val dataSeeder: DataSeeder) {

    suspend fun reCreateDb(req: ServerRequest): ServerResponse {
        dataSeeder.recreateAndSeedDb()
        return ServerResponse.noContent().buildAndAwait()
    }
}
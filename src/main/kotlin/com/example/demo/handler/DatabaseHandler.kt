package com.example.demo.handler

import com.example.demo.configuration.FlywayConfiguration
import com.example.demo.seeders.DataSeeder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

// for demo purposes only
@Component
class DatabaseHandler() {

    @Autowired
    lateinit var flywayConfiguration: FlywayConfiguration

    @Autowired
    lateinit var dataSeeder: DataSeeder

    suspend fun reCreateDb(req: ServerRequest): ServerResponse {
        flywayConfiguration.flyway().clean()
        flywayConfiguration.flyway().migrate()
        dataSeeder.seed()
        return ServerResponse.noContent().buildAndAwait()
    }
}
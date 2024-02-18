package com.example.demo.router

import com.example.demo.database.DatabaseHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router {

    @Bean
    fun routes(databaseHandler: DatabaseHandler) = coRouter {
        "/api".nest {
            POST("/database/recreate", databaseHandler::reCreateDb)
        }
    }
}
package com.example.demo.router

import com.example.demo.handler.DatabaseHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

// for demo purposes only
@Configuration
class Router {

    @Bean
    fun routes(databaseHandler: DatabaseHandler) = coRouter {
        "/api".nest {
            POST("/database/recreate", databaseHandler::reCreateDb)
        }
    }
}
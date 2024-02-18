package com.example.demo.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    @Bean
    fun routes(databaseHandler: DatabaseHandler) = coRouter {
        "/api".nest {
            POST("/database/recreate", databaseHandler::reCreateDb)
        }
    }
}
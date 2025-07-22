package com.example.demo

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration


@Configuration
class TestDatabaseConfig {
    @Value("\${spring.r2dbc.name:}")
    private lateinit var dbName: String

    @PostConstruct
    fun abortIfNotTestDatabase() {
        if (!dbName.contains("test", ignoreCase = true)) {
            throw IllegalStateException("Tests must use a test database! Current DB: $dbName")
        }
    }
}
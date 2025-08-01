package com.example.demo.configuration

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(
    @Value("\${spring.flyway.url}") private val url: String,
    @Value("\${spring.flyway.user}") private val user: String,
    @Value("\${spring.flyway.password}") private val password: String,
    @Value("\${spring.flyway.clean-disabled}") private val cleanDisabled: String
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(
                url,
                user,
                password
            )
            .cleanDisabled(cleanDisabled.toBoolean())
            .load()
    }
}
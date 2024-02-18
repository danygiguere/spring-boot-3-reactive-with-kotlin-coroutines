package com.example.demo.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfiguration(private val env: Environment) {

    val cleanDisabled = env.getRequiredProperty("spring.flyway.cleanDisabled");
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        env.getRequiredProperty("spring.flyway.url"),
                        env.getRequiredProperty("spring.flyway.user"),
                        env.getRequiredProperty("spring.flyway.password")
                )
                .cleanDisabled(cleanDisabled.toBoolean())
                .load()
    }
}
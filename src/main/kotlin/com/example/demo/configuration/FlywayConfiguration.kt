package com.example.demo.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfiguration(private val env: Environment) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        env.getRequiredProperty("spring.flyway.url"),
                        env.getRequiredProperty("spring.flyway.user"),
                        env.getRequiredProperty("spring.flyway.password")
                )
                .cleanDisabled(env.getProperty("spring.flyway.clean-disabled", Boolean::class.java, true))
                .load()
    }
}
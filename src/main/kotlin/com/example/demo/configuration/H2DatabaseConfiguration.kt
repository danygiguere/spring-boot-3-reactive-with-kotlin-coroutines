package com.example.demo.configuration

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class H2DatabaseConfiguration {

    @Bean
    fun initializeH2Database(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        // this line below is necessary because I still haven't figured out how to make flyway work with h2
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("db/migration/V1_1__create_users_and_posts_and_images_tables.sql")))
        return initializer
    }
}
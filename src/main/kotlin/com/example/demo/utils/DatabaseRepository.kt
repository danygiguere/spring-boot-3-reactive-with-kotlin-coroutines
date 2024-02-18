package com.example.demo.utils

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository

@Repository
class DatabaseRepository(private val databaseClient: DatabaseClient) {

    suspend fun dropUsersTable(): Unit =
            databaseClient.sql("DROP TABLE users").await()

    suspend fun createUsersTable(): Unit =
            databaseClient.sql("""CREATE TABLE IF NOT EXISTS users (
            id bigint NOT NULL AUTO_INCREMENT,
            username varchar(255) NOT NULL DEFAULT '',
            email varchar(255) NOT NULL DEFAULT '',
            phoneNumber varchar(255) NOT NULL DEFAULT '',
            PRIMARY KEY (id));""").await()
    suspend fun dropPostsTable(): Unit =
            databaseClient.sql("DROP TABLE posts").await()

    suspend fun createPostsTable(): Unit =
            databaseClient.sql("""CREATE TABLE IF NOT EXISTS posts (
            id bigint NOT NULL AUTO_INCREMENT,
            userId bigint NOT NULL,
            title varchar(255) NOT NULL DEFAULT '',
            description varchar(1000) NOT NULL DEFAULT '',
            PRIMARY KEY (id));""").await()

    suspend fun dropImagesTable(): Unit =
            databaseClient.sql("DROP TABLE images").await()

    suspend fun createImagesTable(): Unit =
            databaseClient.sql("""CREATE TABLE IF NOT EXISTS images (
            id bigint NOT NULL AUTO_INCREMENT,
            postId bigint NOT NULL,
            url varchar(255) NOT NULL DEFAULT '',
            PRIMARY KEY (id)
        );""").await()

}
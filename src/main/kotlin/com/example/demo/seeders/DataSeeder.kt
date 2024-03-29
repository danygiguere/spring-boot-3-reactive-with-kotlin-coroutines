package com.example.demo.seeders

import com.example.demo.configuration.FlywayConfiguration
import com.example.demo.image.ImageRepository
import com.example.demo.post.PostRepository
import com.example.demo.user.UserRepository
import factory.ImageFactory
import factory.PostFactory
import factory.UserFactory
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(val flywayConfiguration: FlywayConfiguration,
    val userRepository: UserRepository,
    val postRepository: PostRepository,
    val imageRepository: ImageRepository
    ) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        runBlocking {
            // On startup, clean the db, migrate it and seed it with data
            recreateAndSeedDb()
        }
    }

    suspend fun recreateAndSeedDb() {
        flywayConfiguration.flyway().clean()
        flywayConfiguration.flyway().migrate()
        seed()
    }

    suspend fun seed() {
        val user = UserFactory(userRepository).createOne()
        val post = user.id?.let { PostFactory(postRepository).createOne(it) }
        post?.id?.let { ImageFactory(imageRepository).createOne(it) }

        val users = UserFactory(userRepository).createMany(9)
        for (singleUser in users) {
            singleUser.id?.let { userId ->
                val posts = PostFactory(postRepository).createMany(2, userId)
                for (singlePost in posts) {
                    singlePost.id?.let { postId ->
                        ImageFactory(imageRepository).createMany(2, postId)
                    }
                }
            }
        }


    }

}
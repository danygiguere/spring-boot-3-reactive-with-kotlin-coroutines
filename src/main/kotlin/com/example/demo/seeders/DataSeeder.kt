package com.example.demo.seeders

import com.example.demo.configuration.FlywayConfiguration
import com.example.demo.image.ImageRepository
import com.example.demo.post.PostRepository
import com.example.demo.user.UserRepository
import factory.ImageFactory
import factory.PostFactory
import factory.UserFactory
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder() : ApplicationRunner {

    @Autowired
    lateinit var flywayConfiguration: FlywayConfiguration

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var imageRepository: ImageRepository

    override fun run(args: ApplicationArguments) {

        runBlocking {
            // On startup, clean the db, migrate it and seed it with data
            flywayConfiguration.flyway().clean()
            flywayConfiguration.flyway().migrate()
            seed()
        }
    }

    suspend fun seed() {
        val user = UserFactory(userRepository).createOne()
        val post = user.id?.let { PostFactory(postRepository).createOne(it) }
        post?.id?.let { ImageFactory(imageRepository).createOne(it) }

        val users = UserFactory(userRepository).createMany(9)
        for (newUser in users) {
            newUser.id?.let { userId ->
                val postDto = PostFactory(postRepository).createOne(userId)
                postDto.id?.let { ImageFactory(imageRepository).createOne(it) }
            }
        }
    }

}
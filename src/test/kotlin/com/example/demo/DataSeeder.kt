package com.example.demo

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.post.PostRepository
import com.example.demo.feature.user.UserRepository
import com.example.demo.configuration.FlywayConfiguration
import factories.ImageFactory
import factories.PostFactory
import factories.UserFactory
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class DataSeeder(val flywayConfiguration: FlywayConfiguration,
    val userRepository: UserRepository,
    val postRepository: PostRepository,
    val imageRepository: ImageRepository,
                 @Autowired val env: Environment
    ) : ApplicationRunner {

    companion object: KLogging()

    override fun run(args: ApplicationArguments) {
        val shouldSeed = env.getProperty("spring.deleteAndMigrateAndSeedDatabase", Boolean::class.java, false)
        if (shouldSeed) {
            runBlocking {
                recreateAndSeedDb()
            }
        }
    }

    suspend fun recreateAndSeedDb() {
        flywayConfiguration.flyway().clean()
        flywayConfiguration.flyway().migrate()
        seed()
    }

    suspend fun seed() {
        val user = UserFactory(userRepository).createOne("johndoe", "johndoe@test.com", "secret123")
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
package com.example.demo

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.post.PostRepository
import com.example.demo.feature.user.UserRepository
import com.example.demo.configuration.FlywayConfiguration
import factory.ImageFactory
import factory.PostFactory
import factory.UserFactory
import fixture.feature.image.CreateImageDtoFixture
import fixture.feature.post.CreatePostDtoFixture
import fixture.feature.user.CreateUserDtoFixture
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
        CreateUserDtoFixture.createMany(10).forEach { user ->
        val user = UserFactory(userRepository).createOne(user)
            CreatePostDtoFixture.createMany(2) { userId = user.id }.forEach { post ->
                val post = PostFactory(postRepository).createOne()
                CreateImageDtoFixture.createMany(2) { postId = post.id }.forEach { image ->
                    ImageFactory(imageRepository).createOne(image)
                }
            }
        }
    }

}
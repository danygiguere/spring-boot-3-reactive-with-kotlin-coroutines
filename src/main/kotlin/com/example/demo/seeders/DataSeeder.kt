package com.example.demo.seeders

import com.factory.ImageFactory
import com.example.demo.image.ImageRepository
import com.factory.PostFactory
import com.example.demo.post.PostRepository
import com.factory.UserFactory
import com.example.demo.user.UserRepository
import com.example.demo.utils.DatabaseHandler
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder() : ApplicationRunner {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Autowired
    lateinit var databaseHandler: DatabaseHandler

    override fun run(args: ApplicationArguments) {

        runBlocking {
            // On startup, reCreate the db and seed it with data
            databaseHandler.reCreate()
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

}
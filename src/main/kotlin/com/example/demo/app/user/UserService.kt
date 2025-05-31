package com.example.demo.app.user

import com.example.demo.app.image.ImageRepository
import com.example.demo.app.post.PostRepository
import com.example.demo.app.auth.requests.RegisterRequest
import com.example.demo.app.user.dtos.UserDto
import com.example.demo.app.user.dtos.UserWithImagesDto
import com.example.demo.app.user.dtos.UserWithPostsDto
import com.example.demo.app.user.dtos.toUserWithImagesDto
import com.example.demo.app.user.dtos.toUserWithPostsDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,
                  private val postRepository: PostRepository,
                  private val imageRepository: ImageRepository) {
    suspend fun findById(id: Long): UserDto? =
            userRepository.findById(id)

    // oneToMany relationship query example
    suspend fun findByIdWithPosts(id: Long): UserWithPostsDto? = coroutineScope {
        val user = async{findById(id)}
        val posts = async{postRepository.findByUserId(id)}
        return@coroutineScope user.await()?.toUserWithPostsDto()?.copy(posts = posts.await())
    }

    // hasManyThrough relationship query example
    suspend fun findByIdWithImages(id: Long): UserWithImagesDto? = coroutineScope {
        val user = async{findById(id)}
        val images = async {imageRepository.findByUserIdThroughPosts(id)}
        return@coroutineScope user.await()?.toUserWithImagesDto()?.copy(images = images.await())
    }

    suspend fun findByEmail(email: String): UserDto? =
        userRepository.findByEmail(email)

    suspend fun register(registerRequest: RegisterRequest): UserDto? =
            userRepository.register(registerRequest)

}
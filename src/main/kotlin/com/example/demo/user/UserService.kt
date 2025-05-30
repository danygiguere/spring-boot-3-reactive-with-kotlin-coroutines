package com.example.demo.user

import com.example.demo.image.ImageRepository
import com.example.demo.post.PostRepository
import com.example.demo.user.dtos.*
import com.example.demo.auth.requests.RegisterRequest
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
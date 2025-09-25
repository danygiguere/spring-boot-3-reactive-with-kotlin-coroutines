package com.example.demo.feature.user

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.post.PostRepository
import com.example.demo.feature.image.toImageDtos
import com.example.demo.feature.post.toPostDtos
import com.example.demo.feature.user.dtos.CreateUserDto
import com.example.demo.feature.user.dtos.UserDto
import com.example.demo.feature.user.dtos.UserWithImagesDto
import com.example.demo.feature.user.dtos.UserWithPostsDto
import com.example.demo.feature.user.dtos.toUserWithImagesDto
import com.example.demo.feature.user.dtos.toUserWithPostsDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,
                  private val postRepository: PostRepository,
                  private val imageRepository: ImageRepository,
                  private val passwordEncoder: PasswordEncoder) {
    suspend fun findById(id: Long): UserDto? =
            userRepository.findById(id)?.toUserDto()

    // oneToMany relationship query example
    suspend fun findByIdWithPosts(id: Long): UserWithPostsDto? = coroutineScope {
        val user = async{findById(id)}
        val posts = async{postRepository.findByUserId(id)?.toPostDtos()}
        return@coroutineScope user.await()?.toUserWithPostsDto()?.copy(posts = posts.await())
    }

    // hasManyThrough relationship query example
    suspend fun findByIdWithImages(id: Long): UserWithImagesDto? = coroutineScope {
        val user = async{findById(id)}
        val images = async {imageRepository.findByUserIdThroughPosts(id)?.toImageDtos()}
        return@coroutineScope user.await()?.toUserWithImagesDto()?.copy(images = images.await())
    }

    suspend fun findByEmail(email: String): UserDto? =
        userRepository.findByEmail(email)?.toUserDto()

    suspend fun create(createUserDto: CreateUserDto): UserDto? {
        val id = userRepository.create(createUserDto)
        return findById(id) ?: throw IllegalStateException("Failed to get user")
    }

    suspend fun updatePassword(userId: Long, currentPassword: String, newPassword: String): Boolean {
        val user = userRepository.findById(userId) ?: return false
        val passwordMatch = passwordEncoder.matches(currentPassword, user.password)

        if (!passwordMatch) {
            return false
        }

        return userRepository.updatePassword(userId, newPassword)
    }

}

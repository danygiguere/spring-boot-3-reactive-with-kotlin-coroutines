package com.example.demo.user

import com.example.demo.post.PostRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,
                  private val postRepository: PostRepository) {
    suspend fun findById(id: Long): UserDto? =
            userRepository.findById(id)

    // oneToMany relationship query example
    suspend fun findByIdWithPosts(id: Long): UserDto? = coroutineScope {
        val user = async{findById(id)}
        val posts = async{postRepository.findByUserId(id)?.toList()}
        return@coroutineScope user.await()?.copy(posts = posts.await())
    }

    suspend fun create(userDto: UserDto): UserDto? =
            userRepository.create(userDto)
}
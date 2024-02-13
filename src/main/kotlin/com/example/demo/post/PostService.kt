package com.example.demo.post

import com.example.demo.image.ImageRepository
import com.example.demo.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository,
                  private val userRepository: UserRepository,
                  private val imageRepository: ImageRepository) {

    suspend fun findAll(): Flow<PostDto>? =
            postRepository.findAll()

    suspend fun findById(id: Long): PostDto? =
            postRepository.findById(id)

    // oneToMany relationship query example
    suspend fun findByIdWithImages(id: Long): PostDto? = coroutineScope {
        val post = async{findById(id)}
        val images = async{imageRepository.findByPostId(id)?.toList()}
        return@coroutineScope post.await()?.copy(images = images.await())
    }

    // belongsTo relationship query example
    suspend fun findByIdWithUser(id: Long): PostDto? = coroutineScope {
        val post = async{findById(id)}
        val user = async{userRepository.findById(id)}
        return@coroutineScope post.await()?.copy(user = user.await())
    }

    suspend fun create(userId: Long, postDto: PostDto): PostDto? =
            postRepository.create(userId, postDto)

    suspend fun update(id: Long, postDto: PostDto): Long =
            postRepository.update(id, postDto)

    suspend fun delete(id: Long): Long =
            postRepository.delete(id)
}
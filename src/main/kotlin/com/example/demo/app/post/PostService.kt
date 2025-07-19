package com.example.demo.app.post

import com.example.demo.app.image.ImageRepository
import com.example.demo.app.post.dtos.CreatePostDto
import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.dtos.PostWithImagesDto
import com.example.demo.app.post.dtos.PostWithUserDto
import com.example.demo.app.post.dtos.toPostWithImagesDto
import com.example.demo.app.post.dtos.toPostWithUserDto
import com.example.demo.app.post.requests.UpdatePostRequest
import com.example.demo.app.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository,
                  private val userRepository: UserRepository,
                  private val imageRepository: ImageRepository) {

    suspend fun findAll(): List<PostDto>? =
            postRepository.findAll()

    suspend fun findById(id: Long): PostDto? =
            postRepository.findById(id)

    // oneToMany relationship query example
    suspend fun findByIdWithImages(id: Long): PostWithImagesDto? = coroutineScope {
        val post = async{findById(id)}
        val images = async{imageRepository.findByPostId(id)}
        return@coroutineScope post.await()?.toPostWithImagesDto()?.copy(images = images.await())
    }

    // belongsTo relationship query example
    suspend fun findByIdWithUser(id: Long): PostWithUserDto? = coroutineScope {
        val post = async{findById(id)}
        val user = async{userRepository.findById(id)}
        return@coroutineScope post.await()?.toPostWithUserDto()?.copy(user = user.await())
    }

    suspend fun create(createPostDto: CreatePostDto): PostDto? =
        postRepository.create(createPostDto)

    suspend fun update(id: Long, updatePostRequest: UpdatePostRequest): Long =
            postRepository.update(id, updatePostRequest)

    suspend fun delete(id: Long): Long =
            postRepository.delete(id)
}
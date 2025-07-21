package com.example.demo.feature.post

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.image.toImageDtos
import com.example.demo.feature.post.dtos.CreatePostDto
import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.feature.post.dtos.PostWithImagesDto
import com.example.demo.feature.post.dtos.PostWithUserDto
import com.example.demo.feature.post.dtos.UpdatePostDto
import com.example.demo.feature.post.dtos.toPostWithImagesDto
import com.example.demo.feature.post.dtos.toPostWithUserDto
import com.example.demo.feature.user.UserRepository
import com.example.demo.feature.user.toUserDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class PostService(val postRepository: PostRepository,
                  private val userRepository: UserRepository,
                  private val imageRepository: ImageRepository) {

    suspend fun findAll(): List<PostDto>? =
            postRepository.findAll()?.toPostDtos()

    suspend fun findById(id: Long): PostDto? =
            postRepository.findById(id)?.toPostDto()

    // oneToMany relationship query example
    suspend fun findByIdWithImages(id: Long): PostWithImagesDto? = coroutineScope {
        val post = async{findById(id)}
        val images = async{imageRepository.findByPostId(id)?.toImageDtos()}
        return@coroutineScope post.await()?.toPostWithImagesDto()?.copy(images = images.await())
    }

    // belongsTo relationship query example
    suspend fun findByIdWithUser(id: Long): PostWithUserDto? = coroutineScope {
        val post = async{findById(id)}
        val user = async{userRepository.findById(id)}
        return@coroutineScope post.await()?.toPostWithUserDto()?.copy(user = user.await()?.toUserDto())
    }

    suspend fun create(createPostDto: CreatePostDto): PostDto {
        val id = postRepository.create(createPostDto)
        return postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun update(updatePostDto: UpdatePostDto): Long =
            postRepository.update(updatePostDto)

    suspend fun delete(id: Long): Long =
            postRepository.delete(id)
}
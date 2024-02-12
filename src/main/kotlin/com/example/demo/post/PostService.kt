package com.example.demo.post

import com.example.demo.user.UserDto
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class PostService(val repository: PostRepository) {

    suspend fun findAll(): Flow<PostDto>? =
            repository.findAll()

    suspend fun findById(id: Long): PostDto? =
            repository.findById(id)

    suspend fun findByIdWithImages(id: Long): PostDto? =
            repository.findByIdWithImages(id)

    suspend fun create(userId: Long, postDto: PostDto): PostDto? =
            repository.create(userId, postDto)

    suspend fun update(id: Long, postDto: PostDto): Long =
            repository.update(id, postDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}
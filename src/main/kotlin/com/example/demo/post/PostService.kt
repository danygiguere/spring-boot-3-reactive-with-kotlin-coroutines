package com.example.demo.post

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class PostService(val repository: PostRepository) {

    suspend fun findAll(): Flow<PostDto>? =
            repository.findAll()

    suspend fun findById(id: Long): PostDto? =
            repository.findById(id)

    suspend fun create(postDto: PostDto): PostDto? =
            repository.create(postDto)

    suspend fun update(id: Long, postDto: PostDto): Long =
            repository.update(id, postDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}
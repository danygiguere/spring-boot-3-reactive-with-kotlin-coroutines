package com.example.demo.image

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ImageService(val repository: ImageRepository) {

    suspend fun findAll(): Flow<ImageDto>? =
            repository.findAll()

    suspend fun findById(id: Long): ImageDto? =
            repository.findById(id)

    suspend fun create(userId: Long, postDto: ImageDto): ImageDto? =
            repository.create(userId, postDto)

    suspend fun update(id: Long, postDto: ImageDto): Long =
            repository.update(id, postDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}
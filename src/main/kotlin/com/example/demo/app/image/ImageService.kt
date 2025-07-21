package com.example.demo.app.image

import com.example.demo.app.image.dtos.CreateImageDto
import com.example.demo.app.image.dtos.ImageDto
import org.springframework.stereotype.Service

@Service
class ImageService(val repository: ImageRepository) {

    suspend fun findAll(): List<ImageDto>? =
            repository.findAll()?.toImageDtos()

    suspend fun findById(id: Long): ImageDto? =
//            repository.findById(id)
            repository.findById(id)?.toImageDto()

    suspend fun create(createImageDto: CreateImageDto): ImageDto? {
        val id = repository.create(createImageDto)
//        return repository.findById(id)
        return repository.findById(id)?.toImageDto()
    }
    suspend fun update(id: Long, imageDto: ImageDto): Long =
            repository.update(id, imageDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}
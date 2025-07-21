package com.example.demo.feature.image

import com.example.demo.feature.image.dtos.CreateImageDto
import com.example.demo.feature.image.dtos.ImageDto
import com.example.demo.feature.image.dtos.UpdateImageDto
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
    suspend fun update(updateImageDto: UpdateImageDto): Long =
            repository.update(updateImageDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}
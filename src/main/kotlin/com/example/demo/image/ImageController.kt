package com.example.demo.image

import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ImageController(private val imageService: ImageService) {

    @GetMapping("/images")
    suspend fun getAll(): ResponseEntity<Flow<ImageDto>?> {
        val response = imageService.findAll()
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/images/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<ImageDto> {
        val response = imageService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/images")
    suspend fun create(@Valid @RequestBody imageDto: ImageDto): ResponseEntity<ImageDto> {
        val response = imageService.create(imageDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/images/{id}")
    suspend fun update(@Valid @PathVariable id: Long, @RequestBody imageDto: ImageDto): ResponseEntity<Long> {
        val response = imageService.update(id, imageDto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/images/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = imageService.delete(id)
        return ResponseEntity.ok(response)
    }
}
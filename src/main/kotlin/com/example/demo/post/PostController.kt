package com.example.demo.post

import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService) {

    companion object: KLogging()

    @GetMapping("/posts")
    suspend fun getAll(): ResponseEntity<Flow<PostDto>?> {
        val response = postService.findAll()
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/posts/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<PostDto> {
        val response = postService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/posts")
    suspend fun create(@Valid @RequestBody postDto: PostDto): ResponseEntity<PostDto> {
        val response = postService.create(postDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/posts/{id}")
    suspend fun update(@Valid @PathVariable id: Long, @RequestBody postDto: PostDto): ResponseEntity<Long> {
        val response = postService.update(id, postDto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/posts/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = postService.delete(id)
        return ResponseEntity.ok(response)
    }
}
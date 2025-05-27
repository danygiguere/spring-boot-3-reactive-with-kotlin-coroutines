package com.example.demo.post

import com.example.demo.post.dto.PostDto
import com.example.demo.post.dto.PostWithImagesDto
import com.example.demo.post.dto.PostWithUserDto
import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
class PostController(private val postService: PostService) {

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

    @GetMapping("/posts/{id}/with-images")
    suspend fun getByIdWithImages(@PathVariable id: Long): ResponseEntity<PostWithImagesDto> {
        val response = postService.findByIdWithImages(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/posts/{id}/with-user")
    suspend fun getByIdWithUser(@PathVariable id: Long): ResponseEntity<PostWithUserDto> {
        val response = postService.findByIdWithUser(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/posts")
    suspend fun create(@Valid @RequestBody postDto: PostDto): ResponseEntity<PostDto> {
        val userId: Long = 1; // for demo only. The userId needs to be taken from the auth user
        val response = postService.create(userId, postDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/posts/{id}")
    suspend fun update(@PathVariable id: Long, @Valid @RequestBody postDto: PostDto): ResponseEntity<Long> {
        val response = postService.update(id, postDto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/posts/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = postService.delete(id)
        return ResponseEntity.ok(response)
    }
}
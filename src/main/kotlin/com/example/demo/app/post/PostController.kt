package com.example.demo.app.post

import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.dtos.PostWithImagesDto
import com.example.demo.app.post.dtos.PostWithUserDto
import com.example.demo.app.post.requests.CreatePostRequest
import com.example.demo.app.post.requests.UpdatePostRequest
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import kotlin.toString


@RestController
class PostController(private val postService: PostService) {

    companion object: KLogging()

    @GetMapping("/posts")
    suspend fun getAll(): ResponseEntity<List<PostDto>?> {
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
    suspend fun create(
        @Valid @RequestBody createPostRequest: CreatePostRequest,
        authentication: Authentication
    ): ResponseEntity<PostDto> {
        val response = postService.create(authentication.principal.toString().toLong(), createPostRequest)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/posts/{id}")
    suspend fun update(
        @PathVariable id: Long,
        @Valid @RequestBody updatePostRequest: UpdatePostRequest,
        authentication: Authentication): ResponseEntity<Long> {

        val post = postService.findById(id)
        if (post == null) {
            return ResponseEntity.notFound().build()
        }
        if (post.userId != authentication.principal.toString().toLong()) {
            return ResponseEntity.status(403).build() // Forbidden
        }

        val response = postService.update(id, updatePostRequest)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/posts/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = postService.delete(id)
        return ResponseEntity.ok(response)
    }
}
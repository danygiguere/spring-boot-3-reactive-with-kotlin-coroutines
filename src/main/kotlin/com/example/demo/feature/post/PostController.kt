package com.example.demo.feature.post

import com.example.demo.feature.post.requests.PostCreatePostWithImageRequest
import com.example.demo.feature.image.ImageService
import com.example.demo.feature.image.dtos.CreateImageDto
import com.example.demo.feature.post.dtos.CreatePostDto
import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.feature.post.dtos.PostWithImagesDto
import com.example.demo.feature.post.dtos.PostWithUserDto
import com.example.demo.feature.post.dtos.UpdatePostDto
import com.example.demo.feature.post.requests.CreatePostRequest
import com.example.demo.feature.post.requests.UpdatePostRequest
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService,
                     private val imageService: ImageService) {

    companion object: KLogging()

    @GetMapping("/api/posts")
    suspend fun getAll(): ResponseEntity<List<PostDto>?> {
        val response = postService.findAll()
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/api/posts/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<PostDto> {
        val response = postService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/api/posts/{id}/with-images")
    suspend fun getByIdWithImages(@PathVariable id: Long): ResponseEntity<PostWithImagesDto> {
        val response = postService.findByIdWithImages(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/api/posts/{id}/with-user")
    suspend fun getByIdWithUser(@PathVariable id: Long): ResponseEntity<PostWithUserDto> {
        val response = postService.findByIdWithUser(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/api/posts")
    suspend fun create(
        @Valid @RequestBody request: CreatePostRequest,
        authentication: Authentication
    ): ResponseEntity<PostDto> {
        val createPostDto = request.let {
            CreatePostDto(
                userId = authentication.principal.toString().toLong(),
                title = it.title!!,
                description = it.description!!
            )
        }
        val post = postService.create(createPostDto)
        return ResponseEntity.ok(post)
    }

    @PostMapping("/api/posts-with-image")
    suspend fun createPostWithImage(
        @Valid @RequestBody request: PostCreatePostWithImageRequest,
        authentication: Authentication
    ): ResponseEntity<PostWithImagesDto> {
        val createPostDto = request.let {
            CreatePostDto(
                userId = authentication.principal.toString().toLong(),
                title = it.title!!,
                description = it.description!!
            )
        }
        val postCreated = postService.create(createPostDto)
        val createImageDto = request.let {
            postCreated?.id?.let { postId ->
                CreateImageDto(
                    postId = postId,
                    url = it.image?.url!!,
                )
            }
        }
        val imageCreated = createImageDto?.let { imageService.create(it) }
        val response = postCreated?.let {
            PostWithImagesDto(
                id = it.id,
                userId = postCreated.userId,
                title = postCreated.title,
                description = postCreated.description,
                createdAt = postCreated.createdAt,
                updatedAt = postCreated.updatedAt,
                images = imageCreated?.let { listOf(it) } ?: emptyList()
            )
        }
        return ResponseEntity.ok(response)
    }

    @PutMapping("/api/posts/{id}")
    suspend fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdatePostRequest,
        authentication: Authentication): ResponseEntity<Long> {
        val updatePostDto = request.let {
            UpdatePostDto(
                id = id,
                userId = authentication.principal.toString().toLong(),
                title = it.title!!,
                description = it.description!!
            )
        }
        val response = postService.update(updatePostDto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/api/posts/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = postService.delete(id)
        return ResponseEntity.ok(response)
    }
}
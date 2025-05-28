package com.example.demo.user

import com.example.demo.user.dtos.UserDto
import com.example.demo.user.dtos.UserWithImagesDto
import com.example.demo.user.dtos.UserWithPostsDto
import com.example.demo.user.requests.CreateUserRequest
import com.example.demo.user.requests.toUserDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/users/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<UserDto> {
        val response = userService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/users/{id}/with-posts")
    suspend fun getByIdWithPosts(@PathVariable id: Long): ResponseEntity<UserWithPostsDto> {
        val response = userService.findByIdWithPosts(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/users/{id}/with-images")
    suspend fun getByIdWithImages(@PathVariable id: Long): ResponseEntity<UserWithImagesDto> {
        val response = userService.findByIdWithImages(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/users")
    suspend fun create(@Valid @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserDto> {
        val response = userService.create(createUserRequest.toUserDto())
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }
}
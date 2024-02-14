package com.example.demo.user

import com.example.demo.user.dto.UserDto
import com.example.demo.user.dto.UserWithImagesDto
import com.example.demo.user.dto.UserWithPostsDto
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
    suspend fun create(@Valid @RequestBody userDto: UserDto): ResponseEntity<UserDto> {
        val response = userService.create(userDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }
}
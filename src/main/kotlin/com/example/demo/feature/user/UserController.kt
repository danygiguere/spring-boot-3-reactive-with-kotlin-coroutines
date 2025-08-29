package com.example.demo.feature.user

import com.example.demo.feature.user.dtos.UserDto
import com.example.demo.feature.user.dtos.UserWithImagesDto
import com.example.demo.feature.user.dtos.UserWithPostsDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/api/users/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<UserDto> {
        val response = userService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/api/users/{id}/with-posts")
    suspend fun getByIdWithPosts(@PathVariable id: Long): ResponseEntity<UserWithPostsDto> {
        val response = userService.findByIdWithPosts(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/api/users/{id}/with-images")
    suspend fun getByIdWithImages(@PathVariable id: Long): ResponseEntity<UserWithImagesDto> {
        val response = userService.findByIdWithImages(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

}
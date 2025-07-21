package com.example.demo.feature.profile

import com.example.demo.feature.profile.requests.CreateProfileRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController {

    @PostMapping("/profile")
    suspend fun create(@Valid @RequestBody createProfileRequest: CreateProfileRequest): ResponseEntity<CreateProfileRequest> {
        return ResponseEntity.ok(createProfileRequest)
    }

}
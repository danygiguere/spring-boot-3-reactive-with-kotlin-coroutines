package com.example.demo.app.post.requests

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdatePostRequest(
    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 255, message = "{title.size}")
    val title: String?,

    @get:NotEmpty()
    @get:Size(min = 6, max = 1000, message = "{description.size}")
    val description: String?,
)
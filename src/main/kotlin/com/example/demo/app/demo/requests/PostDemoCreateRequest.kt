package com.example.demo.app.demo.requests

import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PostDemoCreateRequest(
    @field:Valid
    val userId: String,

    @field:Valid
    @get:Size(min = 6, max = 255, message = "{title.size}")
    val name: String,

    @field:Valid
    val profile: Profile
) {
    data class Profile(
        @field:Valid
        val address: Address,

        val description: String? = null,  // This can be null

        @field:Valid
        val dateOfBirth: LocalDate
    ) {
        data class Address(
            @field:Valid
            @get:Size(min = 6, max = 255, message = "{title.size}")
            val street: String,

            @field:Valid
            @get:Size(min = 6, max = 255, message = "{title.size}")
            val postalCode: String,
        )
    }
}

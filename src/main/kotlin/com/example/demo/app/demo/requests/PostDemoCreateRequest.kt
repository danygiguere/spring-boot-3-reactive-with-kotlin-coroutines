package com.example.demo.app.demo.requests

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PostDemoCreateRequest(
    @get:NotNull()
    val userId: String?,

    @get:NotNull(message = "{name.not_null}")
    @get:Size(min = 6, max = 255, message = "{name.size}")
    val name: String?,

    @field:Valid
    @get:NotNull()
    val profile: Profile?
) {
    data class Profile(
        @field:Valid
        @get:NotNull()
        val address: Address?,

        val description: String? = null,  // This can be null

        @get:NotNull()
        val dateOfBirth: LocalDate?
    ) {
        data class Address(
            @get:NotNull()
            val street: String?,

            @get:NotNull()
            val postalCode: String?,
        )
    }
}

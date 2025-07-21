package com.example.demo.feature.profile.requests

import com.example.demo.validators.IsValidPhoneNumber
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateProfileRequest(
    @get:NotNull()
    @get:NotEmpty()
    @get:IsValidPhoneNumber
    val phoneNumber: String,
)

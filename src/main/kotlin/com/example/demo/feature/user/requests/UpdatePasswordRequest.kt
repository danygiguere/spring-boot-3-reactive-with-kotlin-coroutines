package com.example.demo.feature.user.requests

import com.example.demo.validators.FieldsMatch
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@FieldsMatch(
    first = "newPassword",
    second = "newPasswordConfirmation",
    message = "{password_must_match}"
)
data class UpdatePasswordRequest(
    @field:NotBlank(message = "{field.required}")
    val password: String,

    @field:NotBlank(message = "{field.required}")
    @field:Size(min = 8, max = 25, message = "{password.size}")
    val newPassword: String,

    @field:NotBlank(message = "{field.required}")
    @field:Size(min = 8, max = 25, message = "{password.size}")
    val newPasswordConfirmation: String
)
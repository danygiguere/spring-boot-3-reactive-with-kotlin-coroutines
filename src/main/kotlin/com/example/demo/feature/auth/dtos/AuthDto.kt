package com.example.demo.feature.auth.dtos

import com.example.demo.feature.user.dtos.UserDto

data class AuthDto(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresAtTimestamp: String
)
package com.example.demo.app.auth

import com.example.demo.security.SecurityContextRepository.AuthConstants
import com.example.demo.security.Tokenizer
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service

@Service
class AuthService(private val tokenizer: Tokenizer) {

    fun createAccessTokenCookie(userId: Long?): ResponseCookie {
        val accessToken = tokenizer.createAccessToken(userId)
        val urlEncodedAccessToken = java.net.URLEncoder.encode(accessToken, Charsets.UTF_8.name())
        return ResponseCookie.from(AuthConstants.ACCESS_TOKEN_NAME, urlEncodedAccessToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(tokenizer.accessTokenExpiry * 60L)
            .build()
    }

    fun createRefreshTokenCookie(userId: Long?): ResponseCookie {
        val refreshToken = tokenizer.createRefreshToken(userId)
        val urlEncodedRefreshToken = java.net.URLEncoder.encode(refreshToken, Charsets.UTF_8.name())
        return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_NAME, urlEncodedRefreshToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(tokenizer.refreshTokenExpiry * 60L)
            .build()
    }

}
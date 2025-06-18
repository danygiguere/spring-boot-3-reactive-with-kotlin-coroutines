package com.example.demo.app.auth

import com.example.demo.security.SecurityContextRepository.AuthConstants
import com.example.demo.security.Tokenizer
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.Calendar
import java.util.Date
import kotlin.text.toInt
import kotlin.toString

@Service
class AuthService(private val tokenizer: Tokenizer) {

    fun createAccessTokenCookie(userId: Long?): ResponseCookie {
        val accessToken = tokenizer.createAccessToken(userId)
        val urlEncodedAccessToken = java.net.URLEncoder.encode(accessToken, Charsets.UTF_8.name())
        return ResponseCookie.from(AuthConstants.ACCESS_TOKEN_NAME, urlEncodedAccessToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(tokenizer.accessTokenExpiry * 60L - 1) // subtract 1 second to avoid issues with clock skew
            .build()
    }

    fun createRefreshTokenCookie(userId: Long?): ResponseCookie {
        val refreshToken = tokenizer.createRefreshToken(userId)
        // save refresh token in database. A user can have multiple refresh tokens
        val urlEncodedRefreshToken = java.net.URLEncoder.encode(refreshToken, Charsets.UTF_8.name())
        return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_NAME, urlEncodedRefreshToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(tokenizer.refreshTokenExpiry * 60L - 1) // subtract 1 second to avoid issues with clock skew
            .build()
    }

    fun createAccessTokenExpiresAtCookie(expiry: Duration): ResponseCookie {
        val safeExpiry = expiry.minusSeconds(0) // subtract 10 seconds to avoid clock skew
        val expiresAtTimestamp = System.currentTimeMillis() + safeExpiry.toMillis()
        return ResponseCookie.from("access_token_expires_at", expiresAtTimestamp.toString())
            .httpOnly(false)
            .path("/")
            .sameSite("Lax")
            .build()
    }

}
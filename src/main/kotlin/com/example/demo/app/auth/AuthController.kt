package com.example.demo.app.auth

import com.example.demo.app.auth.requests.LoginRequest
import com.example.demo.security.Tokenizer
import com.example.demo.app.user.UserService
import com.example.demo.app.user.dtos.UserDto
import com.example.demo.app.auth.requests.RegisterRequest
import com.example.demo.security.SecurityContextRepository.AuthConstants
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val userService: UserService,
                     private val tokenizer: Tokenizer,
                     private val passwordEncoder: PasswordEncoder) {

    companion object: KLogging()

    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<String> {
        val newUser = userService.register(request)
        return if (newUser != null) {
            ResponseEntity.ok().body("An email will be sent to you. Please confirm your email by clicking the verification link provided in the email")
        } else {
            logger.error("Error while registering user with email: ${request.email}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /*
        Use this endpoint to log in and set a cookie with the token
        then from javascript you can simply call this api with withCredentials set to true.
        Like: this.http.post<PostDto>('http://localhost:8080/posts', postDto, { withCredentials: true });
     */
    @PostMapping("/login")
    suspend fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserDto> {
        val user = userService.findByEmail(request.email)
        val passwordMatch = passwordEncoder.matches(request.password, user?.password)
        return if (user != null && passwordMatch) {
            val token = tokenizer.createAccessToken(user.id)
            val urlEncodedToken = java.net.URLEncoder.encode(token, Charsets.UTF_8.name())
            val cookie = ResponseCookie.from(AuthConstants.AUTH_COOKIE_NAME, urlEncodedToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .build()

            // val refreshToken = tokenizer.createRefreshToken(user.id)
            // save refresh token in database. A user must can have multiple refresh tokens
            // val refreshCookie = ResponseCookie.from(AuthConstants.REFRESH_COOKIE_NAME, refreshToken)
            //     .httpOnly(true)
            //     .path("/")
            //     .sameSite("Lax")
            //     .maxAge(60 * 60 * 24 * 30) // 30 days
            //     .build()
            ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(user)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    /*
         Use this endpoint to log in and return the token in the response header
         then from javascript you can simply call this api with the token in the Authorization header.
         const headers = new HttpHeaders({
            'Authorization': 'Bearer ...'
         });
         return this.http.post<PostDto>('http://localhost:8080/posts', postDto, { headers });
     */
    @PostMapping("/login-with-token")
    suspend fun loginWithToken(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserDto> {
        val user = userService.findByEmail(request.email)
        val passwordMatch = passwordEncoder.matches(request.password, user?.password)
        return if (user != null && passwordMatch) {
            ResponseEntity.ok().header("Authorization", tokenizer.createAccessToken(user.id)).body(user)

        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/refresh-token")
    suspend fun refreshToken(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserDto> {
       // validate the refresh token and return a new access token
        val user = userService.findByEmail(request.email)
        val userId = 1; // This should be replaced with the actual user ID from the refresh token
        val token = tokenizer.createAccessToken(userId.toLong())
        val urlEncodedToken = java.net.URLEncoder.encode(token, Charsets.UTF_8.name())
        val cookie = ResponseCookie.from(AuthConstants.AUTH_COOKIE_NAME, urlEncodedToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .build()
        ResponseEntity.ok()
            .header("Set-Cookie", cookie.toString())
            .body(user)
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build()
    }

}
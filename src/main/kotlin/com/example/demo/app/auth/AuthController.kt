package com.example.demo.app.auth

import com.example.demo.app.auth.requests.LoginRequest
import com.example.demo.app.auth.requests.RegisterRequest
import com.example.demo.app.user.UserService
import com.example.demo.app.user.dtos.UserDto
import com.example.demo.security.SecurityContextRepository.AuthConstants
import com.example.demo.security.Tokenizer
import jakarta.validation.Valid
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
class AuthController(private val userService: UserService,
                     private val tokenizer: Tokenizer,
                     private val passwordEncoder: PasswordEncoder,
    private val authService: AuthService) {

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
            val accessTokenCookie = authService.createAccessTokenCookie(user.id)
            val refreshTokenCookie = authService.createRefreshTokenCookie(user.id)
            // save refresh token in database. A user can have multiple refresh tokens
            ResponseEntity.ok()
                .headers { headers ->
                    headers.add("Set-Cookie", accessTokenCookie.toString())
                    headers.add("Set-Cookie", refreshTokenCookie.toString())
                }
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
    suspend fun refreshToken(exchange: ServerWebExchange): ResponseEntity<UserDto> {
        val refreshToken = exchange.request.cookies[AuthConstants.REFRESH_TOKEN_NAME]?.firstOrNull()?.value?.removePrefix("Bearer+")
        val decodedJwt = tokenizer.verifyRefreshToken(refreshToken).awaitSingle()
        val user = userService.findById(decodedJwt.subject.toLong())
        // get token by user_id and token from the refresh_token table and verify it matches the refreshToken
        val match = true
        return if(match) {
            val accessTokenCookie = authService.createAccessTokenCookie(user?.id)
            val refreshTokenCookie = authService.createRefreshTokenCookie(user?.id)
            ResponseEntity.ok()
                .headers { headers ->
                    headers.add("Set-Cookie", accessTokenCookie.toString())
                    headers.add("Set-Cookie", refreshTokenCookie.toString())
                }
                .body(user)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}
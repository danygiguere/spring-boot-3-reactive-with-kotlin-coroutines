package com.example.demo.feature.auth

import com.example.demo.feature.auth.requests.LoginRequest
import com.example.demo.feature.auth.requests.RegisterRequest
import com.example.demo.feature.auth.requests.toCreateUserDto
import com.example.demo.feature.user.UserService
import com.example.demo.feature.user.dtos.UserDto
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
        val newUser = userService.create(request.toCreateUserDto())
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
            val accessTokenExpiresAtCookie = authService.createAccessTokenExpiresAtCookie(accessTokenCookie.maxAge)
            val refreshTokenCookie = authService.createRefreshTokenCookie(user.id)
            ResponseEntity.ok()
                .headers { headers ->
                    headers.add("Set-Cookie", accessTokenCookie.toString())
                    headers.add("Set-Cookie", refreshTokenCookie.toString())
                    headers.add("Set-Cookie", accessTokenExpiresAtCookie.toString())
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

        val refreshToken = exchange.request.cookies[AuthConstants.REFRESH_TOKEN_NAME]
            ?.firstOrNull()?.value?.removePrefix("Bearer+")
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val decodedJwt = try {
            tokenizer.verifyRefreshToken(refreshToken).awaitSingle()
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val userId = decodedJwt.subject?.toLongOrNull()
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val user = userService.findById(userId)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        // TODO: Check refresh token validity in DB

        val accessTokenCookie = authService.createAccessTokenCookie(user.id)
        val accessTokenExpiresAtCookie = authService.createAccessTokenExpiresAtCookie(accessTokenCookie.maxAge)
        val refreshTokenCookie = authService.createRefreshTokenCookie(user.id)

        return ResponseEntity.ok()
            .headers { headers ->
                headers.add("Set-Cookie", accessTokenCookie.toString())
                headers.add("Set-Cookie", refreshTokenCookie.toString())
                headers.add("Set-Cookie", accessTokenExpiresAtCookie.toString())
            }
            .body(user)
    }

    @PostMapping("/logout")
    suspend fun logout(exchange: ServerWebExchange): ResponseEntity<String> {
        val accessTokenCookie = authService.invalidateAccessTokenCookie()
        val refreshTokenCookie = authService.invalidateRefreshTokenCookie()
        val accessTokenExpiresAtCookie = authService.invalidateAccessTokenExpiresAtCookie()
        return ResponseEntity.ok()
            .headers { headers ->
                headers.add("Set-Cookie", accessTokenCookie.toString())
                headers.add("Set-Cookie", refreshTokenCookie.toString())
                headers.add("Set-Cookie", accessTokenExpiresAtCookie.toString())
            }
            .body("User logged out successfully")
    }
}
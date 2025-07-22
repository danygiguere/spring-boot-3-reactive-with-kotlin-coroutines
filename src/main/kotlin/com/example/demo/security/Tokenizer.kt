package com.example.demo.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.demo.feature.auth.enums.JWTTypeEnum
import com.example.demo.feature.user.UserRoleEnum
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class Tokenizer {

    @Value("\${app.token.issuer}")
    private val issuer: String? = null

    @Value("\${app.access-token.secret}")
    private val accessTokenSecret: String? = null

    @Value("\${app.refresh-token.secret}")
    private val refreshTokenSecret: String? = null

    @Value("\${app.access-token.expires-minute}")
    public val accessTokenExpiry = 0

    @Value("\${app.refresh-token.expires-minute}")
    public val refreshTokenExpiry = 0

    fun createAccessToken(userId: Long?): String {
        return "Bearer " + tokenize(userId.toString(), accessTokenExpiry, accessTokenSecret.toString(), JWTTypeEnum.ACCESS_TOKEN)
    }

    fun createRefreshToken(userId: Long?): String {
        return "Bearer " + tokenize(userId.toString(), refreshTokenExpiry, refreshTokenSecret.toString(), JWTTypeEnum.REFRESH_TOKEN)
    }

    fun tokenize(userId: String?, expiry: Int, secret: String, type: JWTTypeEnum): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, expiry)
        val expiresAt: Date = calendar.time

        return JWT.create()
            .withIssuer(issuer)
            .withSubject(userId)
            .withClaim("role", UserRoleEnum.ROLE_USER.toString())
            .withClaim("type", type.toString())
            .withExpiresAt(expiresAt)
            .sign(algorithm(secret))
    }

    fun verifyAccessToken(token: String?): Mono<DecodedJWT> {
        return verify(token, accessTokenSecret.toString())
    }

    fun verifyRefreshToken(token: String?): Mono<DecodedJWT> {
        return verify(token, refreshTokenSecret.toString())
    }

    private fun verify(token: String?, secret: String): Mono<DecodedJWT> {
        try {
            //TODO verify other params
            val verifier: JWTVerifier = JWT.require(algorithm(secret)).withIssuer(issuer).build()
            val response = Mono.just(verifier.verify(token))
            return response
        } catch (e: Exception) {
            return Mono.empty()
        }
    }

    private fun algorithm(secret: String): Algorithm {
        return Algorithm.HMAC256(secret)
    }

}
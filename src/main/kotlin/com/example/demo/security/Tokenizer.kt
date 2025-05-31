package com.example.demo.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.demo.user.UserRoleEnum
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class Tokenizer {

    @Value("\${app.token.secret}")
    private val secret: String? = null

    @Value("\${app.token.issuer}")
    private val issuer: String? = null

    @Value("\${app.token.expires-minute}")
    private val expires = 0

    fun createBearerToken(userId: Long?): String {
        return "Bearer " + tokenize(userId.toString())
    }

    fun tokenize(userId: String?): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, expires)
        val expiresAt: Date = calendar.time

        return JWT.create()
            .withIssuer(issuer)
            .withSubject(userId)
            .withClaim("role", UserRoleEnum.ROLE_USER.toString())
            .withExpiresAt(expiresAt)
            .sign(algorithm())
    }

    fun verify(token: String?): Mono<DecodedJWT> {
        try {
            //TODO verify other params
            val verifier: JWTVerifier = JWT.require(algorithm()).withIssuer(issuer).build()
            val response = Mono.just(verifier.verify(token))
            return response
        } catch (e: Exception) {
            return Mono.empty()
        }
    }

    public fun algorithm(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

}
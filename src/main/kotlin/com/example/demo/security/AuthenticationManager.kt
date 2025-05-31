package com.example.demo.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*


@Component
class AuthenticationManager(private val tokenizer: Tokenizer) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.justOrEmpty(authentication.credentials)
            .filter { Objects.nonNull(it) }
            .flatMap { credential -> tokenizer.verify(credential as String) }
            .flatMap { decodedJWT ->
                val userId = decodedJWT.subject
                val role = decodedJWT.getClaim("role").asString()
                val authorities = listOf(SimpleGrantedAuthority(role))
                Mono.just(UsernamePasswordAuthenticationToken(userId, null, authorities))
            }
    }
}
package com.example.demo.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URLDecoder

@Component
class SecurityContextRepository(private val authManager: AuthenticationManager) : ServerSecurityContextRepository {

    object AuthConstants {
        const val AUTH_COOKIE_NAME = "auth"
    }

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val cookie = exchange.request.cookies[AuthConstants.AUTH_COOKIE_NAME]?.firstOrNull()?.value
        val token = cookie?.let { URLDecoder.decode(it, Charsets.UTF_8.name()) }
            ?: exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        return Mono.justOrEmpty(token)
            .filter { header -> header.startsWith("Bearer ") }
            .flatMap { header ->
                val token = header.substring(7)
                val auth = UsernamePasswordAuthenticationToken(token, token)
                authManager.authenticate(auth).map { SecurityContextImpl(it) }
            }
    }

}
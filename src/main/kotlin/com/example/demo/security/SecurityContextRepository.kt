package com.example.demo.security

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.withContext
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(private val authManager: AuthenticationManager) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        return Mono.empty()
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> = mono {
        val cookie = exchange.request.cookies["auth"]?.firstOrNull()?.value
        val decodedCookie = cookie?.let {
            withContext(Dispatchers.IO) {
                java.net.URLDecoder.decode(it, Charsets.UTF_8.name())
            }
        }
        val jwt = decodedCookie?.takeIf { it.isNotEmpty() } ?: exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        if (jwt != null && jwt.startsWith("Bearer ")) {
            val token = jwt.substring(7)
            val auth = UsernamePasswordAuthenticationToken(token, token)
            val authentication = authManager.authenticate(auth).awaitSingle()
            SecurityContextImpl(authentication)
        } else {
            null
        }
    }
}
package com.example.demo.configuration

import com.example.demo.app.post.PostService
import com.example.demo.filters.checkPostOwnershipWebFilter
import com.example.demo.filters.setLocaleWebFilter
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class WebFilterConfig(
    private val postService: PostService
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {

        setLocaleWebFilter(exchange)

        val path = exchange.request.path.value()
        val method = exchange.request.method.name()

        if (!checkPostOwnershipWebFilter(path, method, exchange, postService)) {
            return@mono null
        }
        chain.filter(exchange).awaitFirstOrNull()
    }
}
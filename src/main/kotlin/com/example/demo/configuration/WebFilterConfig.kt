package com.example.demo.configuration

import com.example.demo.app.post.PostService
import com.example.demo.filters.checkPostOwnershipWebFilter
import com.example.demo.filters.setLocaleWebFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange


@Component
class WebFilterConfig(
    private val postService: PostService
) : CoWebFilter() {
    override suspend fun filter(
        exchange: ServerWebExchange,
        chain: CoWebFilterChain
    ) {
        setLocaleWebFilter(exchange)
        val path = exchange.request.path
        val method = exchange.request.method
        if (!checkPostOwnershipWebFilter(path, method, exchange, postService)) {
            return
        }
        chain.filter(exchange)
    }
}
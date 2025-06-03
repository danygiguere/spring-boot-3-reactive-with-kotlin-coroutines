package com.example.demo.filters

import com.example.demo.app.post.PostService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange

suspend fun checkPostOwnershipWebFilter(
    path: String,
    method: String,
    exchange: ServerWebExchange,
    postService: PostService
): Boolean {
    val regex = Regex("""^/posts/(\d+)$""")
    val match = regex.matchEntire(path)
    if ((method == "PUT" || method == "DELETE") && match != null) {
        val id = match.groupValues[1].toLongOrNull()
        val authentication = ReactiveSecurityContextHolder.getContext().awaitFirstOrNull()?.authentication
        val userId = authentication?.principal?.toString()?.toLongOrNull()

        if (id == null || userId == null) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            return false
        }
        val post = postService.findById(id)
        if (post == null) {
            exchange.response.statusCode = HttpStatus.NOT_FOUND
            return false
        }
        if (post.userId != userId) {
            exchange.response.statusCode = HttpStatus.FORBIDDEN
            return false
        }
    }
    return true
}
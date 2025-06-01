package com.example.demo.filters

import com.example.demo.app.post.PostService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

//@Component
//class PostOwnershipWebFilter(
//    private val postService: PostService
//) : WebFilter {
//    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {
//        val path = exchange.request.path.value()
//        val method = exchange.request.method.name()
//
//        val regex = Regex("""^/posts/(\d+)$""")
//        val match = regex.matchEntire(path)
//        if ((method == "PUT" || method == "DELETE") && match != null) {
//            val id = match.groupValues[1].toLongOrNull()
//            val authentication = ReactiveSecurityContextHolder.getContext().awaitFirstOrNull()?.authentication
//            val userId = authentication?.principal?.toString()?.toLongOrNull()
//
//            if (id == null || userId == null) {
//                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
//                return@mono null
//            }
//            val post = postService.findById(id.toString().toLong())
//            if (post == null) {
//                exchange.response.statusCode = HttpStatus.NOT_FOUND
//                return@mono null
//            }
//            if (post.userId != userId) {
//                exchange.response.statusCode = HttpStatus.FORBIDDEN
//                return@mono null
//            }
//        }
//        chain.filter(exchange).awaitFirstOrNull()
//    }
//}
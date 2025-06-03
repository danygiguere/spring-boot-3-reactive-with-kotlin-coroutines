package com.example.demo.filters

import com.example.demo.app.post.PostService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.Locale


@Component
class GlobalWebFilter(
    private val postService: PostService
) : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> = mono {

        setLocaleFromExchange(exchange)

        val path = exchange.request.path.value()
        val method = exchange.request.method.name()

        if (!checkPostOwnership(path, method, exchange, postService)) {
            return@mono null
        }
        chain.filter(exchange).awaitFirstOrNull()
    }
}

private suspend fun checkPostOwnership(
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

private fun setLocaleFromExchange(exchange: ServerWebExchange) {
    val localeContext: LocaleContext = exchange.localeContext
    val locale = localeContext.locale ?: Locale.ENGLISH
    LocaleContextHolder.setLocale(locale)
}
package com.example.demo.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Configuration
class LocaleConfiguration : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val localeContext: LocaleContext = exchange.localeContext
        val locale = localeContext.locale ?: Locale.ENGLISH
        LocaleContextHolder.setLocale(locale)
        return chain.filter(exchange)
    }
}
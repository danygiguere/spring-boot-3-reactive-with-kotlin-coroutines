package com.example.demo.filters

import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.server.ServerWebExchange
import java.util.Locale

fun setLocaleWebFilter(exchange: ServerWebExchange) {
    val localeContext: LocaleContext = exchange.localeContext
    val locale = localeContext.locale ?: Locale.ENGLISH
    LocaleContextHolder.setLocale(locale)
}
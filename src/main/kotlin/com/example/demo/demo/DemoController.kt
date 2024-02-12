package com.example.demo.demo

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

import kotlinx.coroutines.delay
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

import kotlin.math.abs

@RestController
class DemoController() {

    companion object: KLogging()

    @Autowired
    private val messageSource: ResourceBundleMessageSource? = null

    suspend fun executeFaked1000msCall() {
        delay(1000)
    }

    @GetMapping("/demo")
    suspend fun demo(exchange: ServerWebExchange): String {
        val localeContext: LocaleContext = exchange.localeContext
        val locale = localeContext.locale
        LocaleContextHolder.setLocale(locale)
        return messageSource!!.getMessage("welcome", null, LocaleContextHolder.getLocale())
    }

    @GetMapping("/demo/blocking")
    suspend fun demoBlocking(exchange: ServerWebExchange): String {
        val timeBefore = System.currentTimeMillis()
        // The 2 calls below are executed one after the other
        executeFaked1000msCall()
        executeFaked1000msCall()
        val timeAfter = System.currentTimeMillis()
        val duration = abs(timeBefore - timeAfter)
        return "Number of milliseconds to execute function (containing two 1000ms blocking queries) : $duration ms"
    }

    @GetMapping("/demo/async")
    suspend fun demoAsync(exchange: ServerWebExchange): String = coroutineScope {
        val timeBefore = System.currentTimeMillis()
        // The 2 calls below are executed in parallel (at the same time)
        val durationRequest1 = async{executeFaked1000msCall()}
        val durationRequest2 = async{executeFaked1000msCall()}
        durationRequest1.await()
        durationRequest2.await()
        val timeAfter = System.currentTimeMillis()
        val duration = abs(timeBefore - timeAfter)
        return@coroutineScope "Number of milliseconds to execute this function (containing two 1000ms async/parallel queries) : $duration ms"
    }

}
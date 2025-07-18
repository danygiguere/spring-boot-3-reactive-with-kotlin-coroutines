package com.example.demo.app.demo

import com.example.demo.app.demo.requests.PostDemoCreateRequest
import jakarta.validation.Valid
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.server.ServerWebExchange
import java.time.LocalDateTime
import kotlin.math.abs

@RestController
class DemoController(val messageSource: ResourceBundleMessageSource? = null) {

    companion object: KLogging()
    suspend fun executeFaked1000msCall() {
        delay(1000)
    }
    @GetMapping("/demo")
    fun demo(): String {
        return messageSource!!.getMessage("welcome", null, LocaleContextHolder.getLocale())
    }

    @GetMapping("/demo/blocking")
    fun demoBlocking(exchange: ServerWebExchange): String {
        val timeBefore = System.currentTimeMillis()
        /*
        runBlocking:
        Runs a new coroutine and blocks the current thread interruptibly until its completion.
        This function should not be used from a coroutine.
        It is designed to bridge regular blocking code to libraries that are written in suspending style,
        to be used in main functions and in tests.
         */
        runBlocking {
            executeFaked1000msCall()
            executeFaked1000msCall()
        }
        val timeAfter = System.currentTimeMillis()
        val duration = abs(timeBefore - timeAfter)
        return "Number of milliseconds to execute function (containing two 1000ms queries) : $duration ms"
    }

    @GetMapping("/demo/non-parallel")
    suspend fun demoNonParallel(exchange: ServerWebExchange): String {
        val timeBefore = System.currentTimeMillis()
        // The 2 calls below are executed one after the other
        executeFaked1000msCall()
        executeFaked1000msCall()
        val timeAfter = System.currentTimeMillis()
        val duration = abs(timeBefore - timeAfter)
        return "Number of milliseconds to execute function (containing two 1000ms queries) : $duration ms"
    }

    @GetMapping("/demo/parallel")
    suspend fun demoParallel(exchange: ServerWebExchange): String = coroutineScope {
        val timeBefore = System.currentTimeMillis()
        // The 2 calls below are executed in parallel (at the same time)
        val durationRequest1 = async{executeFaked1000msCall()}
        val durationRequest2 = async{executeFaked1000msCall()}
        durationRequest1.await()
        durationRequest2.await()
        val timeAfter = System.currentTimeMillis()
        val duration = abs(timeBefore - timeAfter)
        return@coroutineScope "Number of milliseconds to execute this function (containing two 1000ms parallel queries) : $duration ms"
    }

    @GetMapping("/demo/webclient")
    suspend fun demoWebclient(): String? {
        val webClient = WebClient.create("http://localhost:8080")

        return webClient.get()
            .uri("/demo")
            .retrieve()
            .awaitBody<String>()
    }

    suspend fun generateFlowNumbers(n: Int): Flow<Int> = flow {
        for (i in 1..n) {
            delay(1)
            println("emit     $i: ${LocalDateTime.now()}")
            emit(i)
        }
    }

    @GetMapping("/demo/flow")
    suspend fun demoFlow(): Int {
        var sum = 0
        generateFlowNumbers(5).collect { number ->
            println("Received $number: ${LocalDateTime.now()}")
            sum+= number
        }
        return sum
    }

    @PostMapping("/demo/create")
    suspend fun create(
        @Valid @RequestBody request: PostDemoCreateRequest
    ): ResponseEntity<PostDemoCreateRequest> {
        return ResponseEntity.ok(request)
    }

}
package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun retrieveGreeting() {
        val result = webTestClient.get()
                .uri("/demo")
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(String::class.java)
                .returnResult()

        Assertions.assertEquals("Welcome to my Spring Boot Kotlin appppp", result.responseBody)
    }

}
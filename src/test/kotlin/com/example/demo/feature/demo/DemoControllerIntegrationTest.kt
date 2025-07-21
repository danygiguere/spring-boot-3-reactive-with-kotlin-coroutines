package com.example.demo.feature.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.Locale
import java.util.ResourceBundle

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `WHEN demo is requested THEN the welcome message is returned`() {
        val bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH)
        val welcomeMessageInEnglish = bundle.getString("welcome")

        val result = webTestClient.get()
                .uri("/demo")
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(String::class.java)
                .returnResult()

        Assertions.assertEquals(welcomeMessageInEnglish, result.responseBody)
    }

    @Test
    fun `WHEN demo is requested with fr locale THEN the French welcome message is returned`() {
        val bundle = ResourceBundle.getBundle("messages", Locale.FRENCH)
        val welcomeMessageInFrench = bundle.getString("welcome")

        val result = webTestClient.get()
            .uri("/demo")
            .header("Accept-Language", "fr")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        // Replace with the actual French message from messages_fr.properties
        Assertions.assertEquals(welcomeMessageInFrench, result.responseBody)
    }

}
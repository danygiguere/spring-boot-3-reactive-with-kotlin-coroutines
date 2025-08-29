package com.example.demo.feature.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.Locale
import java.util.ResourceBundle
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `WHEN demo is requested THEN the welcome message is returned`() {
        val bundle = ResourceBundle.getBundle("messages", Locale.ENGLISH)
        val welcomeMessageInEnglish = bundle.getString("welcome")

        val result = webTestClient.get()
                .uri("/api/demo")
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(String::class.java)
                .returnResult()

        assertThat(result.responseBody, containsString(welcomeMessageInEnglish))
    }

    @Test
    fun `WHEN demo is requested with fr locale THEN the French welcome message is returned`() {
        val bundle = ResourceBundle.getBundle("messages", Locale.FRENCH)
        val welcomeMessageInFrench = bundle.getString("welcome")
        val result = webTestClient.get()
            .uri("/api/demo")
            .header("Accept-Language", "fr")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        assertThat(result.responseBody, containsString(welcomeMessageInFrench))
    }

}
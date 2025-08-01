package com.example.demo.feature.auth

import com.example.demo.feature.user.UserRepository
import com.example.demo.feature.user.dtos.UserDto
import factories.UserFactory
import fixtures.Fixtures
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `GIVEN valid data WHEN registering THEN user is created`() = runTest {

        val registerRequest = Fixtures.registerRequest.createDefault()

        webTestClient.post()
            .uri("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registerRequest)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .isEqualTo("An email will be sent to you. Please confirm your email by clicking the verification link provided in the email")
    }

    @Test
    fun `GIVEN valid credentials WHEN logging in THEN an authorization header and a user are returned`() = runTest {

        val userDto = UserFactory(userRepository).createOne()
        val loginRequest = Fixtures.loginRequest.createDefault().copy(
            email = userDto.email,
            password = "secret123"
        )

        val response = webTestClient.post()
            .uri("/login-with-token")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectHeader().exists("Authorization")
            .expectBody(UserDto::class.java)
            .returnResult()

        val authHeader = response.responseHeaders.getFirst("Authorization")
        Assertions.assertNotNull(authHeader)
        Assertions.assertEquals(userDto.username, response.responseBody?.username)
        Assertions.assertEquals(userDto.email, response.responseBody?.email)
    }

}
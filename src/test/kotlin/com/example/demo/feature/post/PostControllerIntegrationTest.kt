package com.example.demo.feature.post

import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.security.Tokenizer
import factories.PostFactory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationTest(@Autowired val webTestClient: WebTestClient) {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var tokenizer: Tokenizer

    lateinit var bearerToken: String

    @BeforeEach
    fun setUp() {
        bearerToken = tokenizer.createAccessToken(1)
    }

    @Test
    fun `GIVEN valid data and jwt WHEN a post is submitted THEN the post is returned`() {
        runTest {
            // Given
            val postDto = PostFactory(postRepository).makeOne(1)

            // When
            val result = webTestClient.post()
                .uri("/posts")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .bodyValue(postDto)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(PostDto::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertNotNull(result)
            Assertions.assertEquals(postDto.title, result?.title)
        }
    }

    @Test
    fun `GIVEN no JWT WHEN a post is submitted THEN a 401 is returned`() {
        runTest {
            // Given
            val postDto = PostFactory(postRepository).makeOne(1)

            // When
            webTestClient.post()
                .uri("/user/1")
                .bodyValue(postDto)
                .exchange()
                .expectStatus().isEqualTo(401)
        }
    }

    @Test
    fun `GIVEN invalid data WHEN a post is submitted THEN a validation error is returned`() {
        runTest {
            // Given
            val postDto = PostDto(1, 1, "T", "D", LocalDateTime.now(), LocalDateTime.now())

            // When, Then
            webTestClient.post()
                .uri("/posts")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .bodyValue(postDto)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.title").isEqualTo("The field Title must be between 6 and 255 characters long")
                .jsonPath("$.description").isEqualTo("The field Description must be between 6 and 1000 characters long")
        }
    }

    @Test
    fun `WHEN posts are requested THEN the posts are returned`() {
        runTest {
            // Given
            PostFactory(postRepository).createMany(3, 1)

            // When
            val result = webTestClient.get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PostDto::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertNotNull(result!!)
        }
    }

    @Test
    fun `WHEN one post is requested THEN the post is returned`() {
        runTest {
            // Given
            val postDto = PostFactory(postRepository).createOne(1)

            // When
            val result = webTestClient.get()
                .uri("""/posts/${postDto.id}""")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDto::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertNotNull(result!!)
            Assertions.assertEquals(postDto.title, result.title)
            Assertions.assertEquals(postDto.description, result.description)
        }
    }

    @Test
    fun `GIVEN valid data WHEN a post is updated THEN 1 is returned`() {
        runTest {
            // Given
            val postDto = PostFactory(postRepository).createOne(1)

            // When
            val result = webTestClient.put()
                .uri("""/posts/${postDto.id}""")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .bodyValue(postDto)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(Long::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertTrue {
                result?.toInt() == 1
            }
        }
    }

    @Test
    fun `WHEN a post is deleted THEN 1 is returned`() {
        runTest {
            // When
            val result = webTestClient.delete()
                .uri("""/posts/1""")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(Long::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertTrue {
                result?.toInt() == 1
            }
        }
    }

}
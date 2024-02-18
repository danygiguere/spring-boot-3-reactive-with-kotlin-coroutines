package com.example.demo.integration

import com.example.demo.post.PostRepository
import com.example.demo.post.dto.PostDto
import factory.PostFactory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest(@Autowired val webTestClient: WebTestClient) {

    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun `GIVEN valid data WHEN a post is submitted THEN the post is returned`() {
        runBlocking {
            // Given
            val postDto = PostFactory(postRepository).createOne(1)

            // When
            val result = webTestClient.post()
                .uri("/posts")
                .bodyValue(postDto)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(PostDto::class.java)
                .returnResult()
                .responseBody

            // Then
            Assertions.assertTrue {
                result!!.id != null
            }
        }
    }

    @Test
    fun `GIVEN invalid data WHEN a post is submitted THEN a validation error is returned`() {
        runBlocking {
            // Given
            val postDto = PostDto(1, 1, "T", "The Description")

            // When, Then
            webTestClient.post()
                .uri("/posts")
                .bodyValue(postDto)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.title").isEqualTo("The field Title must be between 6 and 255 characters long")
        }
    }

    @Test
    fun `WHEN posts are requested THEN the posts are returned`() {
        runBlocking {
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
    fun `GIVEN valid data WHEN a post is updated THEN 1 is returned`() {
        runBlocking {
            // Given
            val postDto = PostFactory(postRepository).createOne(1)

            // When
            val result = webTestClient.put()
                .uri("""/posts/${postDto.id}""")
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
        runBlocking {
            // When
            val result = webTestClient.delete()
                .uri("""/posts/1""")
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
package com.example.demo

import com.example.demo.post.PostService
import com.example.demo.post.dto.PostDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest(@Autowired val webTestClient: WebTestClient) {

    @MockkBean
    lateinit var postService: PostService

    @Test
    fun createPostTest() {
            val postDto = PostDto(1, 1, "The title", "The Description")

            coEvery { postService.create(1, postDto) } returns postDto
            val result = webTestClient.post()
                    .uri("/posts")
                    .bodyValue(postDto)
                    .exchange()
                    .expectStatus().is2xxSuccessful
                    .expectBody(PostDto::class.java)
                    .returnResult()
                    .responseBody

            Assertions.assertTrue {
                result!!.id != null
            }
    }

}
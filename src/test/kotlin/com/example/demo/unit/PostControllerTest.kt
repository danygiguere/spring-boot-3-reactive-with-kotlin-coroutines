package com.example.demo.unit

import com.factory.PostFactory
import com.example.demo.post.PostRepository
import com.example.demo.post.PostService
import com.example.demo.post.dto.PostDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import kotlinx.coroutines.flow.asFlow
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

    @Autowired
    lateinit var postRepository: PostRepository

    @Test
    fun `GIVEN valid data WHEN a post is submitted THEN the post is returned`() {
            val postDto = PostFactory(postRepository).makeOne(1)

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

    @Test
    fun `GIVEN invalid data WHEN a post is submitted THEN a validation error is returned`() {
        val postDto = PostDto(1, 1, "T", "The Description")

        coEvery { postService.create(1, postDto) } returns postDto
        webTestClient.post()
            .uri("/posts")
            .bodyValue(postDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isNotEmpty()
            .jsonPath("$.title").isEqualTo("The field Title must be between 6 and 255 characters long")
    }

    @Test
    fun `WHEN posts are requested THEN the posts are returned`() {
        val posts = PostFactory(postRepository).makeMany(3, 1)

        // Stubbing the service call to return the list of posts
        coEvery { postService.findAll() } returns posts.asFlow()

        // Performing the HTTP request
        val result = webTestClient.get()
            .uri("/posts")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(PostDto::class.java)
            .returnResult()
            .responseBody

        // Assertions
        Assertions.assertNotNull(result)
        Assertions.assertEquals(posts.size, result!!.size)

        for (i in posts.indices) {
            Assertions.assertEquals(posts[i].id, result[i].id)
            Assertions.assertEquals(posts[i].title, result[i].title)
            Assertions.assertEquals(posts[i].description, result[i].description)
        }
    }

    @Test
    fun `GIVEN valid data WHEN a post is updated THEN 1 is returned`() {
        val postDto = PostFactory(postRepository).makeOne(1)

        coEvery { postService.update(1, postDto) } returns 1
        val result = webTestClient.put()
            .uri("""/posts/${postDto.id}""")
            .bodyValue(postDto)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Long::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result?.toInt() == 1
        }
    }

    @Test
    fun `WHEN a post is deleted THEN 1 is returned`() {
        coEvery { postService.delete(1) } returns 1
        val result = webTestClient.delete()
            .uri("""/posts/1""")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Long::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            result?.toInt() == 1
        }
    }

}
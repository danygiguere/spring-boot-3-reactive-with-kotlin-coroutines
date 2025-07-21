package com.example.demo.feature.post

import com.example.demo.feature.image.ImageService
import factories.PostFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerUnitTest() {

    private lateinit var postController: PostController

    private val postService: PostService = mockk()
    private val imageService: ImageService = mockk()

    @BeforeEach
    fun setUp() {
        postController = PostController(postService, imageService)
    }

    @Test
    fun `GIVEN valid data WHEN a post is submitted THEN the post is returned`() = runTest {
        // Given
        val createPostRequest = PostFactory(mockk()).makeCreatePostRequest()
        val postDto = PostFactory(mockk()).makePostDto(1, 1, createPostRequest.title, createPostRequest.description)
        coEvery { postService.create(any()) } returns postDto

        val authentication = mockk<Authentication>()
        coEvery { authentication.principal } returns "1"

        // When
        val result = postController.create(createPostRequest, authentication)

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(postDto.id, result.body?.id)
        Assertions.assertEquals(postDto.title, result.body?.title)
        Assertions.assertEquals(postDto.description, result.body?.description)
    }

    @Test
    fun `WHEN posts are requested THEN the posts are returned`() = runTest {
        // Given
        val posts = PostFactory(mockk()).makeMany(3, 1)
        coEvery { postService.findAll() } returns posts

        // When
        val result = postController.getAll()

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(posts.size, result.body?.size)

        for (i in posts.indices) {
            Assertions.assertEquals(posts[i].id, result.body?.get(i)?.id)
            Assertions.assertEquals(posts[i].title, result.body?.get(i)?.title)
            Assertions.assertEquals(posts[i].description, result.body?.get(i)?.description)
        }
    }

    @Test
    fun `GIVEN valid data WHEN a post is updated THEN 1 is returned`() = runTest {
        // Given
        val userId = 1L
        val postId = 1L
        val updatePostRequest = PostFactory(mockk()).makeUpdatePostRequest()
        val updatePostDto = PostFactory(mockk()).makeUpdatePostDto(postId, userId,updatePostRequest.title, updatePostRequest.description)

        val authentication = mockk<Authentication>()
        coEvery { authentication.principal } returns userId

        coEvery { postService.update( updatePostDto) } returns 1

        // When
        val result = postController.update(postId, updatePostRequest, authentication)

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(1, result.body)
    }

    @Test
    fun `WHEN a post is deleted THEN 1 is returned`() = runTest {
        // Given
        coEvery { postService.delete(1) } returns 1

        // When
        val result = postController.delete(1)

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(1, result.body)
    }

}
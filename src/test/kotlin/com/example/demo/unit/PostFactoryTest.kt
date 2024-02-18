package com.example.demo.unit

import com.example.demo.post.PostRepository
import factory.PostFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostFactoryTest {

    @Autowired
    lateinit var postRepository: PostRepository

    private lateinit var postFactory: PostFactory

    @BeforeEach
    fun setUp() {
        postFactory = PostFactory(postRepository)
    }

    @Test
    fun `GIVEN param userId = x WHEN makeOne is called THEN a post with userId x is returned`() {
        // Given
        val userId: Long = 1

        // When
        val postDto = postFactory.makeOne(userId)

        // Then
        assertEquals(userId, postDto.userId)
    }

    @Test
    fun `GIVEN a quantity and userId = x WHEN makeMany is called THEN a list of posts with userId is returned`() {
        // Given
        val quantity = 3
        val userId: Long = 1

        // When
        val posts = postFactory.makeMany(quantity, userId)

        // Then
        assertEquals(quantity, posts.size)

        posts.forEachIndexed { index, postDto ->
            val id = index + 1L
            assertEquals(id, postDto.id)
            assertEquals(userId, postDto.userId)
            assertNotNull(postDto.title)
            assertNotNull(postDto.description)
        }
    }
}
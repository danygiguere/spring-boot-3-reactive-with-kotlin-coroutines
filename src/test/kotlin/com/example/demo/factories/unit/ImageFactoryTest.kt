package com.example.demo.factories.unit

import factories.ImageFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageFactoryTest {

    private lateinit var imageFactory: ImageFactory

    @BeforeEach
    fun setUp() {
        imageFactory = ImageFactory(mockk())
    }

    @Test
    fun `GIVEN param postId = x WHEN makeOne is called THEN an image with postId x is returned`() {
        // Given
        val postId: Long = 1

        // When
        val imageDto = imageFactory.makeOne(postId)

        // Then
        assertEquals(postId, imageDto.postId)
    }

    @Test
    fun `GIVEN a quantity and postId = x WHEN makeMany is called THEN a list of images with postId x is returned`() {
        // Given
        val quantity = 3
        val postId: Long = 1

        // When
        val images = imageFactory.makeMany(quantity, postId)

        // Then
        assertEquals(quantity, images.size)

        images.forEachIndexed { index, imageDto ->
            val id = index + 1L
            assertEquals(id, imageDto.id)
            assertEquals(postId, imageDto.postId)
            assertNotNull(imageDto.url)
        }
    }
}
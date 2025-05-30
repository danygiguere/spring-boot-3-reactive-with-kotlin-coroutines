package com.example.demo.factories.integration

import com.example.demo.image.ImageRepository
import factories.ImageFactory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageFactoryTest() {

    @Autowired
    lateinit var imageRepository: ImageRepository

    private lateinit var imageFactory: ImageFactory

    @BeforeEach
    fun setUp() {
        imageFactory = ImageFactory(imageRepository)
    }

    @Test
    fun `GIVEN param postId = x WHEN createOne is called THEN an image with postId x is returned`() {
        runTest {
            // Given
            val postId: Long = 1

            // When
            val imageDto = imageFactory.createOne(postId)

            // Then
            assertEquals(postId, imageDto.postId)
        }
    }

    @Test
    fun `GIVEN a quantity and a postId = x WHEN createMany is called THEN a list of images with postId x is returned`() {
        runTest {
            // Given
            val quantity = 3
            val postId: Long = 1

            // When
            val images = imageFactory.createMany(quantity, postId)

            // Then
            assertEquals(quantity, images.size)

            images.forEachIndexed { index, imageDto ->
                assertNotNull(imageDto.id)
                assertEquals(postId, imageDto.postId)
                assertNotNull(imageDto.url)
            }
        }
    }
}
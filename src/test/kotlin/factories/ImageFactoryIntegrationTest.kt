package factories

import com.example.demo.app.image.ImageRepository
import com.example.demo.app.post.PostRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(
    classes = [com.example.demo.DemoApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageFactoryIntegrationTest() {

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Autowired
    lateinit var postRepository: PostRepository

    private lateinit var imageFactory: ImageFactory

    private lateinit var postFactory: PostFactory

    @BeforeEach
    fun setUp() {
        imageFactory = ImageFactory(imageRepository)
        postFactory = PostFactory(postRepository)
    }

    @Test
    fun `GIVEN param postId = x WHEN createOne is called THEN an image with postId x is returned`() {
        runTest {
            // Given
            val postDto = postFactory.createOne(1)

            // When
            val imageDto = imageFactory.createOne(postDto.id)

            // Then
            assertEquals(postDto.id, imageDto.postId)
        }
    }

    @Test
    fun `GIVEN a quantity and a postId = x WHEN createMany is called THEN a list of images with postId x is returned`() {
        runTest {
            // Given
            val quantity = 3
            val postDto = postFactory.createOne(1)

            // When
            val images = imageFactory.createMany(quantity, postDto.id)

            // Then
            assertEquals(quantity, images.size)

            images.forEachIndexed { index, imageDto ->
                assertNotNull(imageDto.id)
                assertEquals(postDto.id, imageDto.postId)
                assertNotNull(imageDto.url)
            }
        }
    }
}
package factories.tests

import com.example.demo.DemoApplication
import com.example.demo.feature.post.PostRepository
import factories.PostFactory
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
    classes = [DemoApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostFactoryIntegrationTest() {

    @Autowired
    lateinit var postRepository: PostRepository

    private lateinit var postFactory: PostFactory

    @BeforeEach
    fun setUp() {
        postFactory = PostFactory(postRepository)
    }

    @Test
    fun `GIVEN param userId = x WHEN createOne is called THEN a post with userId x is returned`() {
        runTest {
            // Given
            val userId: Long = 1

            // When
            val postDto = postFactory.createOne(userId)

            // Then
            assertEquals(userId, postDto.userId)
        }
    }

    @Test
    fun `GIVEN a quantity and userId = x WHEN createMany is called THEN a list of posts with userId x is returned`() {
        runTest {
            // Given
            val quantity = 3
            val userId: Long = 1

            // When
            val posts = postFactory.createMany(quantity, userId)

            // Then
            assertEquals(quantity, posts.size)

            posts.forEachIndexed { index, postDto ->
                assertNotNull(postDto.id)
                assertEquals(userId, postDto.userId)
                assertNotNull(postDto.title)
                assertNotNull(postDto.description)
            }
        }
    }
}
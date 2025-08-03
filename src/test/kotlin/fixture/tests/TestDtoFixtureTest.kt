package fixture.tests

import com.example.demo.DemoApplication
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(
    classes = [DemoApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TestDtoFixtureTest {

    @Test
    fun `makeOne default`() = runTest {
        // Given & When
        val dto = TestDtoFixture.createOne()

        // Then
        Assertions.assertNotNull(dto)
        Assertions.assertNotNull(dto.username)
        Assertions.assertNotNull(dto.email)
        Assertions.assertNotNull(dto.password)
    }

    @Test
    fun `makeOne with custom values`() = runTest {
        // Given
        val customUsername = "CustomUser"
        val customEmail = "custom@example.com"
        val customPassword = "CustomPass123!"

        // When
        val dto = TestDtoFixture.createOne {
            username = customUsername
            email = customEmail
            password = customPassword
        }

        // Then
        Assertions.assertNotNull(dto)
        Assertions.assertEquals(customUsername, dto.username)
        Assertions.assertEquals(customEmail, dto.email)
        Assertions.assertEquals(customPassword, dto.password)
    }

    @Test
    fun `makeMany default`() = runTest {
        // Given
        val count = 3

        // When
        val dtos = TestDtoFixture.createMany(count = count)

        // Then
        Assertions.assertNotNull(dtos)
        Assertions.assertEquals(count, dtos.size)

        // Verify all usernames are unique
        val usernames = dtos.map { it.username }.toSet()
        Assertions.assertEquals(count, usernames.size, "All DTOs should have unique usernames")
    }

    @Test
    fun `makeMany with custom values`() = runTest {
        // Given
        val count = 2
        val customPassword = "CustomPass123!"

        // When
        val dtos = TestDtoFixture.createMany(count = count) {
            password = customPassword
        }

        // Then
        Assertions.assertNotNull(dtos)
        Assertions.assertEquals(count, dtos.size)

        dtos.forEachIndexed { index, dto ->
            Assertions.assertEquals(customPassword, dto.password)
        }
    }
}
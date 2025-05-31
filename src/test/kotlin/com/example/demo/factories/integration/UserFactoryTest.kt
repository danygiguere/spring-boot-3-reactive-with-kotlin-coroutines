package com.example.demo.factories.integration

import com.example.demo.auth.requests.RegisterRequest
import com.example.demo.user.UserRepository
import com.example.demo.user.dtos.UserDto
import factories.UserFactory
import io.mockk.coEvery
import io.mockk.coVerify
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
class UserFactoryTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    private lateinit var userFactory: UserFactory

    @BeforeEach
    fun setUp() {
        userFactory = UserFactory(userRepository)
    }

    @Test
    fun `WHEN createOne is called THEN a user is returned`() {
        runTest {
            // When
            val userDto = userFactory.createOne()

            // Then
            assertNotNull(userDto.id)
            assertNotNull(userDto.username)
            assertNotNull(userDto.email)
        }
    }

    @Test
    fun `GIVEN a quantity WHEN createMany is called THEN a list of users is returned`() {
        runTest {
            // Given
            val quantity = 3

            // When
            val users = userFactory.createMany(quantity)

            // Then
            assertEquals(quantity, users.size)

            users.forEachIndexed { index, userDto ->
                assertNotNull(userDto.id)
                assertNotNull(userDto.username)
                assertNotNull(userDto.email)
            }
        }
    }

    @Test
    fun `GIVEN params WHEN createUser is called THEN repository is called and UserDto is returned`() = runTest {
        // Given
        val username = "testuser"
        val email = "testuser@test.com"

        // When
        val result = userFactory.createUser(username, email)

        // Then
        assertEquals(username, result.username)
        assertEquals(email, result.email)
        assertEquals("secret", result.password)
    }
}
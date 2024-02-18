package com.example.demo.unit

import com.example.demo.user.UserRepository
import factory.UserFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserFactoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    private lateinit var userFactory: UserFactory

    @BeforeEach
    fun setUp() {
        userFactory = UserFactory(userRepository)
    }

    @Test
    fun `GIVEN no params WHEN makeOne is called THEN a user is returned`() {
        // Create a single user
        val userDto = userFactory.makeOne()

        // Assert that the user is not null
        assertEquals(1, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
        assertNotNull(userDto.phoneNumber)
    }

    @Test
    fun `GIVEN param userid = x WHEN makeOne is called THEN a user with id x is returned`() {
        // Given
        val userId: Long = 2

        // Create a single user
        val userDto = userFactory.makeOne(userId)

        // Assert that the user is not null
        assertEquals(userId, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
        assertNotNull(userDto.phoneNumber)
    }

    @Test
    fun `GIVEN a quantity WHEN makeMany is called THEN a list of users is returned`() {
        // Given
        val quantity = 3 // Define the number of users to create

        // When
        val users = userFactory.makeMany(quantity)

        // Then
        assertEquals(quantity, users.size, "Number of users in the list should match the specified quantity")

        users.forEachIndexed { index, userDto ->
            val id = index + 1L
            assertEquals(id, userDto.id)
            assertNotNull(userDto.username)
            assertNotNull(userDto.email)
            assertNotNull(userDto.phoneNumber)
        }
    }
}
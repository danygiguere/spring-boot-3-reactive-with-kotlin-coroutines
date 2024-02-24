package com.example.demo.factory.unit

import factory.UserFactory
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserFactoryTest {

    private lateinit var userFactory: UserFactory

    @BeforeEach
    fun setUp() {
        userFactory = UserFactory(mockk())
    }

    @Test
    fun `GIVEN no params WHEN makeOne is called THEN a user is returned`() {
        // When
        val userDto = userFactory.makeOne()

        // Then
        assertEquals(1, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
        assertNotNull(userDto.phoneNumber)
    }

    @Test
    fun `GIVEN param userid = x WHEN makeOne is called THEN a user with id x is returned`() {
        // Given
        val userId: Long = 2

        // When
        val userDto = userFactory.makeOne(userId)

        // Then
        assertEquals(userId, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
        assertNotNull(userDto.phoneNumber)
    }

    @Test
    fun `GIVEN a quantity WHEN makeMany is called THEN a list of users is returned`() {
        // Given
        val quantity = 3

        // When
        val users = userFactory.makeMany(quantity)

        // Then
        assertEquals(quantity, users.size)

        users.forEachIndexed { index, userDto ->
            val id = index + 1L
            assertEquals(id, userDto.id)
            assertNotNull(userDto.username)
            assertNotNull(userDto.email)
            assertNotNull(userDto.phoneNumber)
        }
    }
}
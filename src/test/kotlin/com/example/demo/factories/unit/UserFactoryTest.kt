package com.example.demo.factories.unit

import factories.UserFactory
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
    fun `GIVEN no params WHEN makeUserDto is called THEN default UserDto is returned`() {

        // When
        val userDto = userFactory.makeUserDto()

        // Then
        assertEquals(1, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
        assertNotNull(userDto.password)
        assertNotNull(userDto.createdAt)
        assertNotNull(userDto.updatedAt)
    }

    @Test
    fun `GIVEN all params WHEN makeUserDto is called THEN UserDto has those values`() {
        val now = java.time.LocalDateTime.now()
        // Given, When
        val userDto = userFactory.makeUserDto(42L, "testuser", "test@email.com", "pw", now, now)

        assertEquals(42L, userDto.id)
        assertEquals("testuser", userDto.username)
        assertEquals("test@email.com", userDto.email)
        assertEquals("pw", userDto.password)
        assertEquals(now, userDto.createdAt)
        assertEquals(now, userDto.updatedAt)
    }

    @Test
    fun `GIVEN no params WHEN makeOne is called THEN a user is returned`() {
        // When
        val userDto = userFactory.makeOne()

        // Then
        assertEquals(1, userDto.id)
        assertNotNull(userDto.username)
        assertNotNull(userDto.email)
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
        }
    }

    @Test
    fun `GIVEN no params WHEN makeRegisterRequest is called THEN default RegisterRequest is returned`() {
        // When
        val registerRequest = userFactory.makeRegisterRequest()

        // Then
        assertNotNull(registerRequest.username)
        assertNotNull(registerRequest.email)
        assertNotNull(registerRequest.password)
    }

    @Test
    fun `GIVEN all params WHEN makeRegisterRequest is called THEN RegisterRequest has those values`() {
        // Given, When
        val registerRequest = userFactory.makeRegisterRequest("customuser",
            "custom@email.com",
            "pw")

        assertEquals("customuser", registerRequest.username)
        assertEquals("custom@email.com", registerRequest.email)
        assertEquals("pw", registerRequest.password)
    }
}
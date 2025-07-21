package com.example.demo.security

import com.auth0.jwt.interfaces.DecodedJWT
import com.example.demo.feature.user.UserRepository
import factories.UserFactory
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ContextConfiguration
@ExtendWith(MockitoExtension::class, SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenizerTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenizer: Tokenizer

    private lateinit var userFactory: UserFactory

    @Autowired
    lateinit var environment: Environment

    @BeforeEach
    fun setUp() {
        userFactory = UserFactory(userRepository)
    }

    @Test
    fun `GIVEN user id WHEN createAccessToken is called THEN a token returned`() {
        runTest {
            // Given
            val userDto = userFactory.createOne()

            // When
            val token = tokenizer.createAccessToken(userDto.id).removePrefix("Bearer ")
            // Then
            Assertions.assertNotNull(token)
        }
    }

    @Test
    fun `GIVEN valid bearer WHEN verify is called THEN a DecodedJWT is returned`() {
        runTest {
            // Given
            val userDto = userFactory.createOne()
            val token = tokenizer.createAccessToken(userDto.id).removePrefix("Bearer ")

            // When
            val response: DecodedJWT = tokenizer.verifyAccessToken(token).awaitSingle()

            // Then
            Assertions.assertNotNull(response)
            Assertions.assertEquals(response.subject,  userDto.id.toString())
            Assertions.assertEquals(response.issuer,  environment.getProperty("app.token.issuer"))
        }
    }
}
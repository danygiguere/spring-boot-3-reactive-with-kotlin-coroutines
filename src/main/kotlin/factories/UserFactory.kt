package factories

import com.example.demo.app.auth.requests.LoginRequest
import com.example.demo.app.user.UserRepository
import com.example.demo.app.user.dtos.UserDto
import com.example.demo.app.auth.requests.RegisterRequest
import io.bloco.faker.Faker
import java.time.LocalDateTime

class UserFactory(val userRepository: UserRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeUserDto(id: Long = 1L,
                            username: String? = null,
                            email: String? = null,
                            password: String? = null,
                            createdAt: LocalDateTime? = null,
                            updatedAt: LocalDateTime? = null): UserDto {
        val usernameSeed = username ?: (faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase())
        val emailSeed = email ?: "$usernameSeed@test.com"
        val passwordSeed = password ?: "secret"
        val createdAtSeed = createdAt ?: LocalDateTime.now()
        val updatedAtSeed = updatedAt ?: LocalDateTime.now()
        return UserDto(id, usernameSeed, emailSeed, passwordSeed, createdAtSeed, updatedAtSeed)
    }

    fun makeOne(id: Long = 1L): UserDto {
        return makeUserDto(id = id)
    }

    fun makeMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { makeOne(it + 1L ) }
    }

    fun makeRegisterRequest(username: String? = null,
                                    email: String? = null,
                                    password: String? = null): RegisterRequest {
        val usernameSeed = username ?: (faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase())
        val emailSeed = email ?: "$usernameSeed@test.com"
        val passwordSeed = password ?: "secret123"
        return RegisterRequest(usernameSeed, emailSeed, passwordSeed)
    }

    fun makeLoginRequest(
        email: String? = null,
        password: String? = null
    ): LoginRequest {
        val emailSeed = email ?: (faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase() + "@test.com")
        val passwordSeed = password ?: "secret123"
        return LoginRequest(emailSeed, passwordSeed)
    }

    suspend fun createOne(username: String? = null,
                           email: String? = null,
                           password: String? = null): UserDto {
        return userRepository.register(makeRegisterRequest(username, email, password))
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { createOne() }
    }
}
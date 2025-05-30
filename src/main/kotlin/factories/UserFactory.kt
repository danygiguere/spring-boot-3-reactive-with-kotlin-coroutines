package factories

import com.example.demo.user.UserRepository
import com.example.demo.user.dtos.UserDto
import com.example.demo.auth.requests.RegisterRequest
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
        return (0 until quantities).map { makeOne(id = it + 1L ) }
    }

    fun makeRegisterRequest(username: String? = null,
                                    email: String? = null,
                                    password: String? = null): RegisterRequest {
        val usernameSeed = username ?: (faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase())
        val emailSeed = email ?: "$usernameSeed@test.com"
        val passwordSeed = password ?: "secret"
        return RegisterRequest(usernameSeed, emailSeed, passwordSeed)
    }

    suspend fun createUser(username: String? = null,
                           email: String? = null,
                           password: String? = null): UserDto {
        return userRepository.register(makeRegisterRequest(username, email, password))
    }

    suspend fun createOne(): UserDto {
        return createUser()
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { createUser() }
    }
}
package factories

import com.example.demo.user.UserRepository
import com.example.demo.user.dtos.UserDto
import com.example.demo.user.requests.CreateUserRequest
import io.bloco.faker.Faker

class UserFactory(val userRepository: UserRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeOne(userId: Long = 1L): UserDto {
        val name = faker.name.firstName().lowercase()+faker.name.lastName().lowercase()
        return UserDto(userId, name, "$name@test.com", "secret",null, null)
    }

    fun makeMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { makeOne(it + 1L ) }
    }

    private fun makeCreateUserRequest(username: String? = null,
                                      email: String? = null,
                                      password: String? = null): CreateUserRequest {
        val usernameSeed = username ?: (faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase())
        val emailSeed = email ?: "$usernameSeed@test.com"
        val passwordSeed = password ?: "secret"
        return CreateUserRequest(usernameSeed, emailSeed, passwordSeed)
    }

    suspend fun createUser(username: String? = null,
                           email: String? = null): UserDto {
        return userRepository.create(makeCreateUserRequest(username, email))
    }

    suspend fun createOne(): UserDto {
        return createUser()
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { createUser() }
    }
}
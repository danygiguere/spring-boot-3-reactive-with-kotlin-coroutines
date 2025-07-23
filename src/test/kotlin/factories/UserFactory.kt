package factories

import com.example.demo.feature.user.UserRepository
import com.example.demo.feature.user.dtos.CreateUserDto
import com.example.demo.feature.user.dtos.UserDto
import com.example.demo.feature.user.toUserDto
import fixtures.Fixtures

class UserFactory(val userRepository: UserRepository) {

    suspend fun createOne(): UserDto {
        val registerRequest = Fixtures.createUserDtoFixture.createDefault()
        val id = userRepository.create(registerRequest)
        return userRepository.findById(id)?.copy(password = null)?.toUserDto()
            ?: throw IllegalStateException("Failed to create user")
    }

    suspend fun createOne(username: String): UserDto {
        val email = "${username}@test.com"
        val password = "secret123"
        val id = userRepository.create(CreateUserDto(username, email, password))
        return userRepository.findById(id)?.copy(password = null)?.toUserDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map {
            createOne()
        }
    }
}
package factories

import com.example.demo.feature.user.UserRepository
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

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map {
            createOne()
        }
    }
}
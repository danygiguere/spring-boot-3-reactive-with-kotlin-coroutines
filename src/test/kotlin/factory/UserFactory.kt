package factory

import com.example.demo.feature.user.UserRepository
import com.example.demo.feature.user.dtos.CreateUserDto
import com.example.demo.feature.user.dtos.UserDto
import com.example.demo.feature.user.toUserDto
import fixture.feature.user.CreateUserDtoFixture

class UserFactory(val userRepository: UserRepository) {

    suspend fun createOne(): UserDto {
        val createUserDto = CreateUserDtoFixture.createOne()
        val id = userRepository.create(createUserDto)
        return userRepository.findById(id)?.copy(password = null)?.toUserDto()
            ?: throw IllegalStateException("Failed to create user")
    }

    suspend fun createOne(createUserDto: CreateUserDto): UserDto {
        val id = userRepository.create(createUserDto)
        return userRepository.findById(id)?.copy(password = null)?.toUserDto()
            ?: throw IllegalStateException("Failed to create user")
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map {
            val id = userRepository.create(CreateUserDtoFixture.createOne())
            userRepository.findById(id)?.toUserDto() ?: throw IllegalStateException("Failed to get user")
        }
    }

    suspend fun createMany(users: List<CreateUserDto>): List<UserDto> {
        return users.map { createUserDto ->
            val id = userRepository.create(createUserDto)
            userRepository.findById(id)?.toUserDto() ?: throw IllegalStateException("Failed to get user with id: $id")
        }
    }
}

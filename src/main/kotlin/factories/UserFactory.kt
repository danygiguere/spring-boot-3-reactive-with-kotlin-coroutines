package factories

import com.example.demo.user.UserRepository
import com.example.demo.user.dtos.UserDto
import io.bloco.faker.Faker

class UserFactory(val userRepository: UserRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeOne(userId: Long = 1L): UserDto {
        val name = faker.name.firstName().lowercase()+faker.name.lastName().lowercase()
        val phoneNumber = faker.phoneNumber.areaCode()+"-"+faker.phoneNumber.exchangeCode()+"-"+faker.phoneNumber.subscriberNumber()
        return UserDto(userId, name, "$name@test.com", phoneNumber, null, null)
    }

    fun makeMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { makeOne(it + 1L ) }
    }

    suspend fun createOne(): UserDto {
        return userRepository.create(makeOne())
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { userRepository.create(makeOne()) }
    }
}
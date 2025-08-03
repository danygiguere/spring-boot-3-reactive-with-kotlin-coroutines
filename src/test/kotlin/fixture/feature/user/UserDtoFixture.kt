package fixture.feature.user

import com.example.demo.feature.user.dtos.UserDto
import fixture.base.Fixture
import java.time.LocalDateTime

object UserDtoFixture : Fixture<UserDto, UserDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): UserDto = builder.build()

    class Builder {
        var id: Long = 1
        var username: String = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}"
        var email: String = "${username}@example.com"
        var password: String = "${faker.internet.password(minLength = 12, maxLength = 30)}!1Aa"
        var createdAt: LocalDateTime = LocalDateTime.now()
        var updatedAt: LocalDateTime = LocalDateTime.now()

        fun build(): UserDto = UserDto(
            id = id,
            username = username,
            email = email,
            password = password,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
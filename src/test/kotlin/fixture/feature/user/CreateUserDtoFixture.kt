package fixture.feature.user

import com.example.demo.feature.user.dtos.CreateUserDto
import fixture.base.Fixture

object CreateUserDtoFixture : Fixture<CreateUserDto, CreateUserDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): CreateUserDto = builder.build()

    class Builder {
        var username: String = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}"
        var email: String = "${username}@example.com"
        var password: String = "secret123"

        fun build(): CreateUserDto = CreateUserDto(
            username = username,
            email = email,
            password = password,
        )
    }

}
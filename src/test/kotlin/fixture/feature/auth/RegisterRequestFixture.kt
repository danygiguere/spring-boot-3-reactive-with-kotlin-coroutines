package fixture.feature.auth

import com.example.demo.feature.auth.requests.RegisterRequest
import fixture.base.Fixture

object RegisterRequestFixture : Fixture<RegisterRequest, RegisterRequestFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): RegisterRequest = builder.build()

    class Builder {
        var username: String = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}"
        var email: String = "${username}@example.com"
        var password: String = "secret123"
        var password_confirmation: String = password

        fun build(): RegisterRequest = RegisterRequest(
            username = username,
            email = email,
            password = password,
            password_confirmation = password_confirmation
        )
    }

}
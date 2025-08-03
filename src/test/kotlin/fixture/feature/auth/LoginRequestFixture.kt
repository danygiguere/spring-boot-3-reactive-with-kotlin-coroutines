package fixture.feature.auth

import com.example.demo.feature.auth.requests.LoginRequest
import fixture.base.Fixture

object LoginRequestFixture : Fixture<LoginRequest, LoginRequestFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): LoginRequest = builder.build()

    class Builder {
        var email: String = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}@example.com"
        var password: String = "${faker.internet.password(minLength = 12, maxLength = 30)}!1Aa"

        fun build(): LoginRequest = LoginRequest(
            email = email,
            password = password,
        )
    }

}
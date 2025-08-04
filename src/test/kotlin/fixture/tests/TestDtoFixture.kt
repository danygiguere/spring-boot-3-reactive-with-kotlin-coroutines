package fixture.tests

import fixture.base.Fixture

object TestDtoFixture : Fixture<TestDto, TestDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): TestDto = builder.build()

    class Builder {
        var username: String = "${faker.name.firstName().lowercase()}.${faker.name.lastName().lowercase()}"
        var email: String = "${username}@example.com"
        var password: String = "secret123"

        fun build(): TestDto = TestDto(
            username = username,
            email = email,
            password = password,
        )
    }

}
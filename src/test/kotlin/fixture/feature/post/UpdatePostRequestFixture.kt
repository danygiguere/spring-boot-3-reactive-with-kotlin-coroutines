package fixture.feature.post

import com.example.demo.feature.post.requests.UpdatePostRequest
import fixture.base.Fixture

object UpdatePostRequestFixture : Fixture<UpdatePostRequest, UpdatePostRequestFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): UpdatePostRequest = builder.build()

    class Builder {
        var title: String = faker.lorem.sentence()
        var description: String = faker.lorem.paragraph()

        fun build(): UpdatePostRequest = UpdatePostRequest(
            title = title,
            description = description
        )
    }

}
package fixture.feature.post

import com.example.demo.feature.post.requests.CreatePostRequest
import fixture.base.Fixture

object CreatePostRequestFixture : Fixture<CreatePostRequest, CreatePostRequestFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): CreatePostRequest = builder.build()

    class Builder {
        var title: String = faker.lorem.sentence()
        var description: String = faker.lorem.paragraph()

        fun build(): CreatePostRequest = CreatePostRequest(
            title = title,
            description = description
        )
    }

}
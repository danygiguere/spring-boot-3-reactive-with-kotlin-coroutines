package fixture.feature.post

import com.example.demo.feature.post.dtos.CreatePostDto
import fixture.base.Fixture

object CreatePostDtoFixture : Fixture<CreatePostDto, CreatePostDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): CreatePostDto = builder.build()

    class Builder {
        var userId: Long = 1
        var title: String = faker.lorem.sentence()
        var description: String = faker.lorem.paragraph()

        fun build(): CreatePostDto = CreatePostDto(
            userId = userId,
            title = title,
            description = description
        )
    }

}
package fixture.feature.post

import com.example.demo.feature.post.dtos.PostDto
import fixture.base.Fixture
import java.time.LocalDateTime

object PostDtoFixture : Fixture<PostDto, PostDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): PostDto = builder.build()

    class Builder {
        var id: Long = 1
        var userId: Long = 1
        var title: String = faker.lorem.sentence()
        var description: String = faker.lorem.paragraph()
        var createdAt: LocalDateTime = LocalDateTime.now()
        var updatedAt: LocalDateTime = LocalDateTime.now()


        fun build(): PostDto = PostDto(
            id = id,
            userId = userId,
            title = title,
            description = description,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
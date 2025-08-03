package fixture.feature.post

import com.example.demo.feature.post.dtos.UpdatePostDto
import fixture.base.Fixture

object UpdatePostDtoFixture : Fixture<UpdatePostDto, UpdatePostDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): UpdatePostDto = builder.build()

    class Builder {
        val id: Long = 1
        var userId: Long = 1
        var title: String = faker.lorem.sentence()
        var description: String = faker.lorem.paragraph()

        fun build(): UpdatePostDto = UpdatePostDto(
            id = id,
            userId = userId,
            title = title,
            description = description
        )
    }

}
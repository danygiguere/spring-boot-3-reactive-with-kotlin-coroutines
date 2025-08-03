package fixture.feature.image

import com.example.demo.feature.image.dtos.CreateImageDto
import fixture.base.Fixture

object CreateImageDtoFixture : Fixture<CreateImageDto, CreateImageDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): CreateImageDto = builder.build()

    class Builder {
        var postId: Long = 1
        var url: String = "https://picsum.photos/seed/${faker.number.number(6)}/600/400"

        fun build(): CreateImageDto = CreateImageDto(
            postId = postId,
            url = url
        )
    }

}
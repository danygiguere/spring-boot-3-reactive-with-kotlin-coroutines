package fixture.feature.image

import com.example.demo.feature.image.dtos.ImageDto
import fixture.base.Fixture
import java.time.LocalDateTime

object ImageDtoFixture : Fixture<ImageDto, ImageDtoFixture.Builder>() {

    override fun createBuilder(): Builder = Builder()

    override fun build(builder: Builder): ImageDto = builder.build()

    class Builder {
        var id: Long = 1
        var postId: Long = 1
        var url: String = "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
        var createdAt: LocalDateTime = LocalDateTime.now()
        var updatedAt: LocalDateTime = LocalDateTime.now()

        fun build(): ImageDto = ImageDto(
            id = id,
            postId = postId,
            url = url,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
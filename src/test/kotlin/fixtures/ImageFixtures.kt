package fixtures

import com.example.demo.feature.image.dtos.ImageDto
import com.example.demo.feature.image.dtos.CreateImageDto
import com.example.demo.feature.image.dtos.UpdateImageDto
import java.time.LocalDateTime

object ImageFixtures {

    object ImageDtoFixture : BaseFixture<ImageDto>() {
        override fun builder(): ImageDto {
            return ImageDto(
                id = defaultId,
                postId = 1L,
                url = "https://picsum.photos/seed/${faker.number.number(6)}/600/400",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        override fun createDefault(): ImageDto = builder()
    }

    object CreateImageDtoFixture : BaseFixture<CreateImageDto>() {
        override fun builder(): CreateImageDto {
            return CreateImageDto(
                postId = 1L,
                url = "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
            )
        }

        override fun createDefault(): CreateImageDto = builder()
    }

    object UpdateImageDtoFixture : BaseFixture<UpdateImageDto>() {
        override fun builder(): UpdateImageDto {
            return UpdateImageDto(
                id = defaultId,
                postId = 1L,
                url = "https://picsum.photos/seed/${faker.number.number(6)}/600/400",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        override fun createDefault(): UpdateImageDto = builder()
    }
}
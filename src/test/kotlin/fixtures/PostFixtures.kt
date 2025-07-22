package fixtures

import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.feature.post.dtos.CreatePostDto
import com.example.demo.feature.post.dtos.UpdatePostDto
import com.example.demo.feature.post.dtos.PostWithUserDto
import com.example.demo.feature.post.dtos.PostWithImagesDto
import com.example.demo.feature.post.requests.CreatePostRequest
import com.example.demo.feature.post.requests.UpdatePostRequest
import com.example.demo.feature.post.requests.PostCreatePostWithImageRequest
import java.time.LocalDateTime

object PostFixtures {
    object PostDtoFixture : BaseFixture<PostDto>() {
        override fun builder(): PostDto {
            return PostDto(
                id = defaultId,
                userId = 1L,
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        override fun createDefault(): PostDto = builder()
    }

    object CreatePostDtoFixture : BaseFixture<CreatePostDto>() {
        override fun builder(): CreatePostDto {
            return CreatePostDto(
                userId = 1L,
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
            )
        }

        override fun createDefault(): CreatePostDto = builder()
    }

    object UpdatePostDtoFixture : BaseFixture<UpdatePostDto>() {
        override fun builder(): UpdatePostDto {
            return UpdatePostDto(
                id = defaultId,
                userId = 1L,
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
            )
        }

        override fun createDefault(): UpdatePostDto = builder()
    }

    object PostWithUserDtoFixture : BaseFixture<PostWithUserDto>() {
        override fun builder(): PostWithUserDto {
            return PostWithUserDto(
                id = defaultId,
                userId = 1L,
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                user = UserFixtures.UserDtoFixture.createDefault()
            )
        }

        override fun createDefault(): PostWithUserDto = builder()
    }

    object PostWithImagesDtoFixture : BaseFixture<PostWithImagesDto>() {
        override fun builder(): PostWithImagesDto {
            return PostWithImagesDto(
                id = defaultId,
                userId = 1L,
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                images = emptyList() // Can be populated with ImageDtoFixture if needed
            )
        }

        override fun createDefault(): PostWithImagesDto = builder()
    }

    object CreatePostRequestFixture : BaseFixture<CreatePostRequest>() {
        override fun builder(): CreatePostRequest {
            return CreatePostRequest(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
            )
        }

        override fun createDefault(): CreatePostRequest = builder()
    }

    object UpdatePostRequestFixture : BaseFixture<UpdatePostRequest>() {
        override fun builder(): UpdatePostRequest {
            return UpdatePostRequest(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
            )
        }

        override fun createDefault(): UpdatePostRequest = builder()
    }

    object PostCreatePostWithImageRequestFixture : BaseFixture<PostCreatePostWithImageRequest>() {
        override fun builder(): PostCreatePostWithImageRequest {
            return PostCreatePostWithImageRequest(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                image = PostCreatePostWithImageRequest.Image(
                    url = "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
                )
            )
        }

        override fun createDefault(): PostCreatePostWithImageRequest = builder()
    }
}
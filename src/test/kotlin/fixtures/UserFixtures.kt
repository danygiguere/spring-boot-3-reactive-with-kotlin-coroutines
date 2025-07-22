package fixtures

import com.example.demo.feature.user.dtos.UserDto
import com.example.demo.feature.user.dtos.UserWithPostsDto
import com.example.demo.feature.user.dtos.UserWithImagesDto
import com.example.demo.feature.auth.requests.LoginRequest
import com.example.demo.feature.auth.requests.RegisterRequest
import java.time.LocalDateTime

object UserFixtures {
    val password = "secret123"
    object UserDtoFixture : BaseFixture<UserDto>() {
        override fun builder(): UserDto {
            val username = faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase()
            return UserDto(
                id = defaultId,
                username = username,
                email = "$username@test.com",
                password = password,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        override fun createDefault(): UserDto = builder()
    }

    object UserWithPostsDtoFixture : BaseFixture<UserWithPostsDto>() {
        override fun builder(): UserWithPostsDto {
            val username = faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase()
            return UserWithPostsDto(
                id = defaultId,
                username = username,
                email = "$username@test.com",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                posts = emptyList() // Can be populated with PostDtoFixture if needed
            )
        }

        override fun createDefault(): UserWithPostsDto = builder()
    }

    object UserWithImagesDtoFixture : BaseFixture<UserWithImagesDto>() {
        override fun builder(): UserWithImagesDto {
            val username = faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase()
            return UserWithImagesDto(
                id = defaultId,
                username = username,
                email = "$username@test.com",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                images = emptyList() // Can be populated with ImageDtoFixture if needed
            )
        }

        override fun createDefault(): UserWithImagesDto = builder()
    }

    object LoginRequestFixture : BaseFixture<LoginRequest>() {
        override fun builder(): LoginRequest {
            val username = faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase()
            return LoginRequest(
                email = "$username@test.com",
                password = password
            )
        }

        override fun createDefault(): LoginRequest = builder()
    }

    object RegisterRequestFixture : BaseFixture<RegisterRequest>() {
        override fun builder(): RegisterRequest {
            val username = faker.name.firstName().lowercase() + "." + faker.name.lastName().lowercase()
            return RegisterRequest(
                username = username,
                email = "$username@test.com",
                password = password,
                password_confirmation = password
            )
        }

        override fun createDefault(): RegisterRequest = builder()
    }
}
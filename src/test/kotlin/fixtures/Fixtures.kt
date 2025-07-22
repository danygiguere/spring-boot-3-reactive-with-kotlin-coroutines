// src/test/kotlin/fixtures/Fixtures.kt
package fixtures

/**
 * Main entry point for accessing all fixtures
 */
object Fixtures {
    // User
    val userDto = UserFixtures.UserDtoFixture
    val userWithPostsDto = UserFixtures.UserWithPostsDtoFixture
    val userWithImagesDto = UserFixtures.UserWithImagesDtoFixture
    val loginRequest = UserFixtures.LoginRequestFixture
    val registerRequest = UserFixtures.RegisterRequestFixture

    // Post
    val postDto = PostFixtures.PostDtoFixture
    val createPostDto = PostFixtures.CreatePostDtoFixture
    val updatePostDto = PostFixtures.UpdatePostDtoFixture
    val postWithUserDto = PostFixtures.PostWithUserDtoFixture
    val postWithImagesDto = PostFixtures.PostWithImagesDtoFixture
    val createPostRequest = PostFixtures.CreatePostRequestFixture
    val updatePostRequest = PostFixtures.UpdatePostRequestFixture
    val postCreatePostWithImageRequest = PostFixtures.PostCreatePostWithImageRequestFixture

    // Image
    val imageDto = ImageFixtures.ImageDtoFixture
    val createImageDto = ImageFixtures.CreateImageDtoFixture
    val updateImageDto = ImageFixtures.UpdateImageDtoFixture
}

// Example 1: Create a default user DTO
//val user = Fixtures.userDto.createDefault()

// Example 2: Create a custom user DTO
//val customUser = Fixtures.userDto.builder()
//    .copy(username = "customUser")
//    .copy(email = "custom@example.com")
//    .build()

// Example 3: Create multiple posts
//val posts = Fixtures.postDto.createMany(5)

// Example 4: Create a post with a custom title
//val customPost = Fixtures.postDto.builder()
//    .copy(title = "Custom Title")
//    .build()

// Example 5: Create a login request
//val loginRequest = Fixtures.loginRequest.createDefault()
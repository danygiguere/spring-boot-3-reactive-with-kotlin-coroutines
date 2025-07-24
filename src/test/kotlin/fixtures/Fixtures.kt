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
    val createUserDtoFixture = UserFixtures.CreateUserDtoFixture

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
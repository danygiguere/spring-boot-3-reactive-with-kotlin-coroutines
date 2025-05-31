package factories

import com.example.demo.app.post.PostRepository
import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.requests.CreatePostRequest
import com.example.demo.app.post.requests.UpdatePostRequest
import io.bloco.faker.Faker

class PostFactory(val postRepository: PostRepository) {

    val faker = Faker(locale = "en-CA")

    fun makePostDto(
        id: Long = 1L,
        userId: Long = 1L,
        title: String? = null,
        description: String? = null,
        createdAt: java.time.LocalDateTime? = null,
        updatedAt: java.time.LocalDateTime? = null
    ): PostDto {
        val titleSeed = title ?: faker.book.title()
        val descriptionSeed = description ?: faker.lorem.paragraph()
        return PostDto(id, userId, titleSeed, descriptionSeed, createdAt, updatedAt)
    }

    fun makeOne(userId: Long): PostDto {
        return makePostDto(userId = userId)
    }

    fun makeMany(quantities: Int, userId: Long): List<PostDto> {
        return List(quantities) { makeOne(userId).copy(id = it + 1L) }
    }

    fun makeCreatePostRequest(
        userId: Long,
        title: String? = null,
        description: String? = null
    ): CreatePostRequest {
        return CreatePostRequest(
            userId,
            title ?: faker.book.title(),
            description ?: faker.lorem.paragraph()
        )
    }

    fun makeUpdatePostRequest(
        id: Long,
        userId: Long,
        title: String? = null,
        description: String? = null
    ): UpdatePostRequest {
        return UpdatePostRequest(
            id,
            userId,
            title ?: faker.book.title(),
            description ?: faker.lorem.paragraph()
        )
    }

    suspend fun createOne(userId: Long): PostDto {
        return postRepository.create(userId,makeCreatePostRequest(userId))
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map { postRepository.create(userId,makeCreatePostRequest(userId)) }
    }

}
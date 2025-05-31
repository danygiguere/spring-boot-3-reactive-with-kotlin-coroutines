package factories

import com.example.demo.post.PostRepository
import com.example.demo.post.dtos.PostDto
import com.example.demo.post.requests.CreatePostRequest
import io.bloco.faker.Faker

class PostFactory(val postRepository: PostRepository) {

    val faker = Faker(locale = "en-CA")

    fun makePostDto(
        id: Long? = null,
        userId: Long? = null,
        title: String? = null,
        description: String? = null,
        createdAt: java.time.LocalDateTime? = null,
        updatedAt: java.time.LocalDateTime? = null
    ): PostDto {
        val idSeed = id ?: 1L
        val userIdSeed = userId ?: 1L
        val titleSeed = title ?: faker.book.title()
        val descriptionSeed = description ?: faker.lorem.paragraph()
        return PostDto(idSeed, userIdSeed, titleSeed, descriptionSeed, createdAt, updatedAt)
    }

    fun makeOne(userId: Long): PostDto {
        return makePostDto(null, userId)
    }

    fun makeMany(quantities: Int, userId: Long): List<PostDto> {
        return List(quantities) { makeOne(userId).copy(id = it + 1L) }
    }

    fun makeCreatePostRequest(userId: Long): CreatePostRequest {
        return CreatePostRequest(userId, faker.book.title(), faker.lorem.paragraph())
    }

    suspend fun createOne(userId: Long): PostDto {
        return postRepository.create(userId,makeCreatePostRequest(userId))
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map { postRepository.create(userId,makeCreatePostRequest(userId)) }
    }

}
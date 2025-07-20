package factories

import com.example.demo.app.post.PostRepository
import com.example.demo.app.post.dtos.CreatePostDto
import com.example.demo.app.post.dtos.PostDto
import com.example.demo.app.post.dtos.UpdatePostDto
import com.example.demo.app.post.requests.CreatePostRequest
import com.example.demo.app.post.requests.UpdatePostRequest
import io.bloco.faker.Faker
import java.time.LocalDateTime

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
        val createdAtSeed = createdAt ?: LocalDateTime.now()
        val updatedAtSeed = updatedAt ?: LocalDateTime.now()
        return PostDto(id, userId, titleSeed, descriptionSeed, createdAtSeed, updatedAtSeed)
    }

    fun makeCreatePostDto(
        userId: Long = 1L,
        title: String? = null,
        description: String? = null
    ): CreatePostDto {
        val titleSeed = title ?: faker.book.title()
        val descriptionSeed = description ?: faker.lorem.paragraph()
        return CreatePostDto( userId, titleSeed, descriptionSeed)
    }

    fun makeUpdatePostDto(
        id: Long = 1L,
        userId: Long = 1L,
        title: String? = null,
        description: String? = null
    ): UpdatePostDto {
        val titleSeed = title ?: faker.book.title()
        val descriptionSeed = description ?: faker.lorem.paragraph()
        return UpdatePostDto(id, userId, titleSeed, descriptionSeed)
    }

    fun makeOne(userId: Long): PostDto {
        return makePostDto(userId = userId)
    }

    fun makeMany(quantities: Int, userId: Long): List<PostDto> {
        return List(quantities) { makeOne(userId).copy(id = it + 1L) }
    }

    fun makeCreatePostRequest(
        title: String? = null,
        description: String? = null
    ): CreatePostRequest {
        return CreatePostRequest(
            title ?: faker.book.title(),
            description ?: faker.lorem.paragraph()
        )
    }

    fun makeUpdatePostRequest(
        title: String? = null,
        description: String? = null
    ): UpdatePostRequest {
        return UpdatePostRequest(
            title ?: faker.book.title(),
            description ?: faker.lorem.paragraph()
        )
    }

    suspend fun createOne(userId: Long): PostDto {
        val id = postRepository.create(makeCreatePostDto(userId))
        return postRepository.findById(id) ?: throw IllegalStateException("Failed to create post")
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map {
            val id =  postRepository.create(makeCreatePostDto(userId))
            postRepository.findById(id) ?: throw IllegalStateException("Failed to create post")
        }
    }

}
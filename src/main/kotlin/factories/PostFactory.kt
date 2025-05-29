package factories

import com.example.demo.image.dtos.ImageDto
import com.example.demo.post.PostRepository
import com.example.demo.post.dtos.PostDto
import com.example.demo.post.requests.CreatePostRequest
import io.bloco.faker.Faker

class PostFactory(val postRepository: PostRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeOne(userId: Long): PostDto {
        return PostDto(1, userId, faker.book.title(), faker.lorem.paragraph(), null, null)
    }

    fun makeMany(quantities: Int, userId: Long): List<PostDto> {
        return List(quantities) { makeOne(userId).copy(id = it + 1L) }
    }

    fun makeCreatePostRequest(userId: Long): CreatePostRequest {
        return CreatePostRequest(userId, faker.book.title(), faker.lorem.paragraph())
    }

    suspend fun createOne(userId: Long): PostDto {
        return postRepository.create(makeOne(userId))
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map { postRepository.create(makeOne(userId)) }
    }

}
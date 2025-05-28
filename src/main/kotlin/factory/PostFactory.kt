package factory

import com.example.demo.post.PostRepository
import com.example.demo.post.dtos.PostDto
import io.bloco.faker.Faker

class PostFactory(val postRepository: PostRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeOne(userId: Long): PostDto {
        return PostDto(1, userId, faker.book.title(), faker.lorem.paragraph())
    }

    fun makeMany(quantities: Int, userId: Long): List<PostDto> {
        return List(quantities) { makeOne(userId).copy(id = it + 1L) }
    }

    suspend fun createOne(userId: Long): PostDto {
        return postRepository.create(makeOne(userId))
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map { postRepository.create(makeOne(userId)) }
    }

}
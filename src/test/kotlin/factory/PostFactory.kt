package factory

import com.example.demo.feature.post.PostRepository
import com.example.demo.feature.post.dtos.CreatePostDto
import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.feature.post.toPostDto
import fixture.feature.post.CreatePostDtoFixture

class PostFactory(private val postRepository: PostRepository) {

    suspend fun createOne(): PostDto {
        val createPostDto = CreatePostDtoFixture.createOne()
        val id = postRepository.create(createPostDto)
        return postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createOne(createPostDto: CreatePostDto): PostDto {
        val id = postRepository.create(createPostDto)
        return postRepository.findById(id)?.toPostDto()
            ?: throw IllegalStateException("Failed to create post")
    }

    suspend fun createMany(quantities: Int): List<PostDto> {
        return (0 until quantities).map {
            val id = postRepository.create(CreatePostDtoFixture.createOne())
            postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
        }
    }

    suspend fun createMany(posts: List<CreatePostDto>): List<PostDto> {
        return posts.map { createPostDto ->
            val id = postRepository.create(createPostDto)
            postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post with id: $id")
        }
    }

}

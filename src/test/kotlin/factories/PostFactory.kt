package factories

import com.example.demo.feature.post.PostRepository
import com.example.demo.feature.post.dtos.CreatePostDto
import com.example.demo.feature.post.dtos.PostDto
import com.example.demo.feature.post.toPostDto
import fixtures.Fixtures

class PostFactory(val postRepository: PostRepository) {

    suspend fun createOne(): PostDto {
        val createPostDto = Fixtures.createPostDto.createDefault()
        val id = postRepository.create(createPostDto)
        return postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createOne(userId: Long): PostDto {
        val createPostDto = Fixtures.createPostDto.createDefault()
            .copy(userId = userId)
        val id = postRepository.create(createPostDto)
        return postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createMany(quantities: Int, userId: Long): List<PostDto> {
        return (0 until quantities).map {
            val createPostDto = Fixtures.createPostDto.createDefault()
                .copy(userId = userId)
            val id = postRepository.create(createPostDto)
            postRepository.findById(id)?.toPostDto() ?: throw IllegalStateException("Failed to get post")
        }
    }

}
package factories

import com.example.demo.app.image.ImageRepository
import com.example.demo.app.image.dtos.CreateImageDto
import com.example.demo.app.image.dtos.ImageDto
import io.bloco.faker.Faker

class ImageFactory(val imageRepository: ImageRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeImageDto(
        id: Long = 1L,
        postId: Long = 1L,
        url: String? = null,
        createdAt: java.time.LocalDateTime? = null,
        updatedAt: java.time.LocalDateTime? = null
    ): ImageDto {
        val urlSeed = url ?: "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
        return ImageDto(id, postId, urlSeed, createdAt, updatedAt)
    }

    fun makeCreateImageDto(
        postId: Long = 1L,
        url: String? = null
    ): CreateImageDto {
        val urlSeed = url ?: "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
        return CreateImageDto(postId, urlSeed)
    }

    fun makeOne(postId: Long): ImageDto {
        return makeImageDto(postId = postId)
    }

    fun makeMany(quantities: Int, postId: Long): List<ImageDto> {
        return List(quantities) { makeOne(postId).copy(id = it + 1L) }
    }

    suspend fun createOne(postId: Long): ImageDto {
        val id = imageRepository.create(makeCreateImageDto(postId))
        return imageRepository.findById(id = id) ?: throw IllegalStateException("Failed to get image")
    }

    suspend fun createMany(quantities: Int, postId: Long): List<ImageDto> {
        return (0 until quantities).map {
            val id =  imageRepository.create(makeCreateImageDto(postId))
            imageRepository.findById(id) ?: throw IllegalStateException("Failed to get post")
        }
    }
}
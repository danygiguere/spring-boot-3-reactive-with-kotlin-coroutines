package factories

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.image.dtos.CreateImageDto
import com.example.demo.feature.image.dtos.ImageDto
import com.example.demo.feature.image.toImageDto
import io.bloco.faker.Faker
import java.time.LocalDateTime

class ImageFactory(val imageRepository: ImageRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeImageDto(
        id: Long = 1L,
        postId: Long = 1L,
        url: String? = null,
        createdAt: LocalDateTime? = null,
        updatedAt: LocalDateTime? = null
    ): ImageDto {
        val urlSeed = url ?: "https://picsum.photos/seed/${faker.number.number(6)}/600/400"
        val createdAtSeed = createdAt ?: LocalDateTime.now()
        val updatedAtSeed = updatedAt ?: LocalDateTime.now()
        return ImageDto(id, postId, urlSeed, createdAtSeed, updatedAtSeed)
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
        return imageRepository.findById(id = id)?.toImageDto() ?: throw IllegalStateException("Failed to get image")
    }

    suspend fun createMany(quantities: Int, postId: Long): List<ImageDto> {
        return (0 until quantities).map {
            val id =  imageRepository.create(makeCreateImageDto(postId))
            imageRepository.findById(id)?.toImageDto() ?: throw IllegalStateException("Failed to get post")
        }
    }
}
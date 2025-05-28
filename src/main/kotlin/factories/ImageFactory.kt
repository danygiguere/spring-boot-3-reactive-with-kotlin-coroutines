package factories

import com.example.demo.image.dtos.ImageDto
import com.example.demo.image.ImageRepository
import io.bloco.faker.Faker

class ImageFactory(val imageRepository: ImageRepository) {

    val faker = Faker(locale = "en-CA")
    fun makeOne(postId: Long): ImageDto {
        return ImageDto(1, postId, "https://placehold.co/600x400")
    }

    fun makeMany(quantities: Int, postId: Long): List<ImageDto> {
        return List(quantities) { makeOne(postId).copy(id = it + 1L) }
    }

    suspend fun createOne(postId: Long): ImageDto {
        return imageRepository.create(makeOne(postId))
    }

    suspend fun createMany(quantities: Int, postId: Long): List<ImageDto> {
        return (0 until quantities).map { imageRepository.create(makeOne(postId)) }
    }
}
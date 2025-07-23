package factories

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.image.dtos.ImageDto
import com.example.demo.feature.image.toImageDto
import fixtures.Fixtures

class ImageFactory(val imageRepository: ImageRepository) {

    suspend fun createOne(): ImageDto {
        val createImageDto = Fixtures.createImageDto.createDefault()

        val id = imageRepository.create(createImageDto)
        return imageRepository.findById(id)?.toImageDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createOne(postId: Long): ImageDto {
        val createImageDto = Fixtures.createImageDto.createDefault()
            .copy(postId = postId)
        val id = imageRepository.create(createImageDto)
        return imageRepository.findById(id)?.toImageDto() ?: throw IllegalStateException("Failed to get post")
    }

    suspend fun createMany(quantities: Int, postId: Long): List<ImageDto> {
        return (0 until quantities).map {
            val createImageDto = Fixtures.createImageDto.createDefault()
                .copy(postId = postId)
            val id =  imageRepository.create(createImageDto)
            imageRepository.findById(id)?.toImageDto() ?: throw IllegalStateException("Failed to get post")
        }
    }
}
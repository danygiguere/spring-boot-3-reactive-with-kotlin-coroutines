package factory

import com.example.demo.feature.image.ImageRepository
import com.example.demo.feature.image.dtos.CreateImageDto
import com.example.demo.feature.image.dtos.ImageDto
import com.example.demo.feature.image.toImageDto
import fixture.feature.image.CreateImageDtoFixture

class ImageFactory(private val imageRepository: ImageRepository) {

    suspend fun createOne(): ImageDto {
        val createImageDto = CreateImageDtoFixture.createOne()
        val id = imageRepository.create(createImageDto)
        return imageRepository.findById(id)?.toImageDto()
            ?: throw IllegalStateException("Failed to create image")
    }


    suspend fun createOne(createImageDto: CreateImageDto): ImageDto {
        val id = imageRepository.create(createImageDto)
        return imageRepository.findById(id)?.toImageDto()
            ?: throw IllegalStateException("Failed to create image")
    }

    suspend fun createMany(quantities: Int): List<ImageDto> {
        return (0 until quantities).map {
            val id = imageRepository.create(CreateImageDtoFixture.createOne())
            imageRepository.findById(id)?.toImageDto() ?: throw IllegalStateException("Failed to get image")
        }
    }

    suspend fun createMany(images: List<CreateImageDto>): List<ImageDto> {
        return images.map { createImageDto ->
            val id = imageRepository.create(createImageDto)
            imageRepository.findById(id)?.toImageDto()
                ?: throw IllegalStateException("Failed to get image with id: $id")
        }
    }
}

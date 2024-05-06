package com.ropotdaniel.full_blog.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import org.springframework.core.io.ResourceLoader
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.FileSystemUtils
import java.nio.file.Files
import java.nio.file.Paths

class ImageServiceTest {

    private val resourceLoader: ResourceLoader = mock(ResourceLoader::class.java)
    private val imageService = ImageService(resourceLoader)

    @Test
    fun saveImage() {
        // Create a mock MultipartFile
        val originalFilename = "test.jpg"
        val content = "test data".toByteArray()
        val file = MockMultipartFile(originalFilename, originalFilename, "image/jpeg", content)
//        given(resourceLoader.getResource("classpath:/static/images/")).willReturn()
        // Call the method under test
        val image = imageService.saveImage(file)

        // Verify the returned Image
        assertTrue(image.name.startsWith(originalFilename))
        assertTrue(image.url.startsWith("/images/"))

        // Clean up the created file
        val resource = resourceLoader.getResource("classpath:static/images/")
        val targetLocation = Paths.get(resource.uri.path + image.name)
        if (Files.exists(targetLocation)) {
            FileSystemUtils.deleteRecursively(targetLocation)
        }
    }
}
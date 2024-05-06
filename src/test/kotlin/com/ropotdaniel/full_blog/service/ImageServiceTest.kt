package com.ropotdaniel.full_blog.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.core.io.ResourceLoader
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Paths

class ImageServiceTest {

    private val resourceLoader: ResourceLoader = mock(ResourceLoader::class.java)
    private val imageService = ImageService(resourceLoader)

    @Test
    fun saveImage() {
        val file = MockMultipartFile("file", "test.jpg", "image/jpeg", "test".toByteArray())

        val imageDO = imageService.saveImage(file)

        assertNotNull(imageDO)
        assertEquals(imageDO.name, imageDO.name)

        val directoryPath = Paths.get(System.getProperty("user.home"), "uploads", "images")
        val destinationPath = directoryPath.resolve(imageDO.name)
        val destinationFile = destinationPath.toFile()

        assertTrue(Files.exists(destinationPath))
        assertTrue(destinationFile.delete())
    }
}
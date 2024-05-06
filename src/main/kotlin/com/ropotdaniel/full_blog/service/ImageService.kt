package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.domainobject.ImageDO
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@Service
class ImageService(private val resourceLoader: ResourceLoader) {
    fun saveImage(file: MultipartFile): ImageDO {
        val newFilename = "${file.originalFilename}-${UUID.randomUUID()}"

        val directoryPath = Paths.get(System.getProperty("user.home"), "uploads", "images")

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath)
        }

        val destinationPath = directoryPath.resolve(newFilename)
        val destinationFile = destinationPath.toFile()

        if (!destinationFile.exists()) {
            destinationFile.createNewFile()
        }

        file.transferTo(destinationFile)

        return ImageDO(name = newFilename, url = "/images/" + newFilename)
    }
}
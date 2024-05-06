package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.domainobject.Image
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*


@Service
class ImageService(private val resourceLoader: ResourceLoader) {
    @Autowired
    private val request: HttpServletRequest? = null

    fun saveImage(file: MultipartFile): Image {
        val newFilename = "${file.originalFilename}-${UUID.randomUUID()}"

        val filePath: String = request?.servletContext?.getRealPath("classpath:static/images/").toString()
        file.transferTo(File(filePath))

        val imageUrl = "/images/" + newFilename
        return Image(name = newFilename, url = imageUrl)
    }
}
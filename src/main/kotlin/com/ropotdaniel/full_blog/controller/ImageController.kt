package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.controller.mapper.ImageMapper
import com.ropotdaniel.full_blog.datatransferobject.ImageDTO
import com.ropotdaniel.full_blog.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ImageController @Autowired constructor(private val imageService: ImageService) {

    @PostMapping("/image")
    fun uploadImage(@RequestParam("file") file: MultipartFile): ImageDTO {
        return ImageMapper.mapToImageDTO(imageService.saveImage(file))
    }

}
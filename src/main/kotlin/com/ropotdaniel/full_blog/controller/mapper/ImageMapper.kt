package com.ropotdaniel.full_blog.controller.mapper

import com.ropotdaniel.full_blog.datatransferobject.ImageDTO
import com.ropotdaniel.full_blog.domainobject.ImageDO

object ImageMapper {
    fun toImageDTO(imageDO: ImageDO): ImageDTO {
        return ImageDTO(imageDO.url)
    }
}
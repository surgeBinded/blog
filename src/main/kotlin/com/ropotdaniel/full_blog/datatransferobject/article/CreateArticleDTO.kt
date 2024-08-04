package com.ropotdaniel.full_blog.datatransferobject.article

data class CreateArticleDTO(
    var title: String,
    var content: String,
    var bannerImageUrl: String?,
    var authorId: Long
)
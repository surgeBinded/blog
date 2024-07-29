package com.ropotdaniel.full_blog.datatransferobject

data class ArticleCreateDTO(
    var title: String,
    var content: String,
    var bannerImageUrl: String?,
    var authorId: Long
)
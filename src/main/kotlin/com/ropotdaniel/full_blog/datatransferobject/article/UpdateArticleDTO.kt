package com.ropotdaniel.full_blog.datatransferobject.article

import java.time.ZonedDateTime

data class UpdateArticleDTO(
    var title: String? = null,
    var content: String? = null,
    var bannerImageUrl: String? = null,
    var dateUpdated: ZonedDateTime = ZonedDateTime.now()
)
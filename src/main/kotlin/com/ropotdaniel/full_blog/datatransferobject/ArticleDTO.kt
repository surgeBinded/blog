package com.ropotdaniel.full_blog.datatransferobject

import java.time.ZonedDateTime

class ArticleDTO(var id: Long,
                 var title: String,
                 var content: String,
                 var bannerImageUrl: String,
                 var dateCreated: ZonedDateTime
)
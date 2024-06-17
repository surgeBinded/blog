package com.ropotdaniel.full_blog.datatransferobject

import java.time.ZonedDateTime

data class ArticleDTO(var id: Long,
                 var title: String,
                 var content: String,
                 var bannerImageUrl: String,
                 var comments: List<CommentDTO>,
                 var dateCreated: ZonedDateTime
)
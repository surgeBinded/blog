package com.ropotdaniel.full_blog.datatransferobject

import com.ropotdaniel.full_blog.domainobject.CommentDO
import java.time.ZonedDateTime

class ArticleDTO(var id: Long,
                 var title: String,
                 var content: String,
                 var bannerImageUrl: String,
                 var comments: List<CommentDO>,
                 var dateCreated: ZonedDateTime
)
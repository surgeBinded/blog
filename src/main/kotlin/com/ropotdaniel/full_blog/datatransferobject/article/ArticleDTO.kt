package com.ropotdaniel.full_blog.datatransferobject.article

import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import java.time.ZonedDateTime

data class ArticleDTO(var id: Long,
                      var title: String,
                      var content: String,
                      var bannerImageUrl: String,
                      var comments: List<CommentDTO>,
                      var author: AuthorDTO,
                      var dateUpdated: ZonedDateTime?,
                      var dateCreated: ZonedDateTime
)
package com.ropotdaniel.full_blog.datatransferobject.comment

import java.time.ZonedDateTime

data class CommentDTO(
    var id: Long,
    var articleId: Long,
    var parentCommentId: Long?,
    var replies: MutableList<ReplyDTO> = mutableListOf(),
    var content: String,
    var authorId: Long,
    var likes: Int,
    var dislikes: Int,
    var deleted: Boolean,
    var dateCreated: ZonedDateTime
)

data class ReplyDTO(
    var id: Long
)
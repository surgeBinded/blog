package com.ropotdaniel.full_blog.datatransferobject.comment

import java.time.ZonedDateTime

data class CommentDTO(
    var id: Long,
    var articleId: Long,
    var parentComment: ParentCommentDTO?,
    var replies: MutableList<ReplyDTO> = mutableListOf(),
    var content: String,
    var likes: Int,
    var dislikes: Int,
    var deleted: Boolean,
    var dateCreated: ZonedDateTime
)

data class ParentCommentDTO(
    var id: Long
)

data class ReplyDTO(
    var id: Long
)
package com.ropotdaniel.full_blog.datatransferobject

data class CommentDTO(
    val id: Long,
    val articleId: Long,
    val parentComment: ParentCommentDTO?,
    val replies: MutableList<ReplyDTO> = mutableListOf(),
    val content: String,
    val likes: Int,
    val dislikes: Int,
    val deleted: Boolean
)

data class ParentCommentDTO(
    val id: Long
)

data class ReplyDTO(
    val id: Long
)
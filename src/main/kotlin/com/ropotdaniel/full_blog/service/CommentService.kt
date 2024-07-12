package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.response.CommentResponse
import com.ropotdaniel.full_blog.domainobject.CommentDO

interface CommentService {
    fun getCommentsByArticleId(articleId: Long, pageNo: Int, pageSize: Int, sortBy: String, sortDir: String): CommentResponse
    fun addComment(comment: CommentDTO): CommentDTO
    fun addReply(parentCommentId: Long, reply: CommentDO): CommentDTO
    fun likeComment(commentId: Long): CommentDTO
    fun dislikeComment(commentId: Long): CommentDTO
    fun editCommentContent(commentId: Long, content: String): CommentDTO
    fun deleteComment(commentId: Long): CommentDTO
}
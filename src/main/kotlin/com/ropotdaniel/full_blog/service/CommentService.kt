package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.comment.UpdateCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.response.CommentResponse

interface CommentService {
    fun getCommentsByArticleId(articleId: Long, pageNo: Int, pageSize: Int, sortBy: String, sortDir: String): CommentResponse
    fun addComment(comment: CommentDTO): CommentDTO
    fun likeComment(commentId: Long): CommentDTO
    fun dislikeComment(commentId: Long): CommentDTO
    fun editComment(commentId: Long, updateCommentDTO: UpdateCommentDTO): CommentDTO
    fun deleteComment(commentId: Long): CommentDTO
}
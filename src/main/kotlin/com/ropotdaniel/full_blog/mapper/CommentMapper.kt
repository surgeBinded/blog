package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.comment.ParentCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.comment.ReplyDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.exceptions.CommentNotFoundException
import org.springframework.stereotype.Component

@Component
object CommentMapper {
    lateinit var commentRepository: CommentRepository
    lateinit var articleRepository: ArticleRepository

    fun toDTO(comment: CommentDO): CommentDTO {
        return CommentDTO(
            id = comment.id,
            articleId = comment.article.id,
            parentComment = comment.parentComment?.let { ParentCommentDTO(it.id) },
            replies = comment.replies.filter { !it.deleted }.map { reply -> ReplyDTO(reply.id) }.toMutableList(),
            content = comment.content,
            likes = comment.likes,
            dislikes = comment.dislikes,
            deleted = comment.deleted,
            dateCreated = comment.dateCreated
        )
    }

    fun toDO(comment: CommentDTO): CommentDO {
        val article = articleRepository.getReferenceById(comment.articleId)
        val parentComment = comment.parentComment?.let {
            commentRepository.findById(it.id).orElseThrow { CommentNotFoundException("Parent comment not found") }
        }
        val replies = comment.replies.map {
            commentRepository.findById(it.id).orElseThrow { CommentNotFoundException("Reply not found") }
        }.toMutableList()

        return CommentDO(
            id = comment.id,
            article = article,
            parentComment = parentComment,
            replies = replies,
            content = comment.content,
            author = article.author,
            likes = comment.likes,
            dislikes = comment.dislikes,
            deleted = comment.deleted,
        )
    }
}
package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ParentCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ReplyDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

private const val COMMENT_NOT_FOUND = "Comment not found"

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
) {

    fun getCommentsByArticleId(articleId: Long): List<CommentDTO> {
        return commentRepository.findByArticleId(articleId)
            .filter { !it.deleted }
            .map { comment -> toCommentDTO(comment) }
    }

    fun addComment(comment: CommentDTO): CommentDTO {
        val createdComment = commentRepository.save(toCommentDO(comment))
        return toCommentDTO(createdComment)
    }

    @Transactional
    fun addReply(parentCommentId: Long, reply: CommentDO): CommentDTO {
        val parentComment = commentRepository.findById(parentCommentId).orElseThrow { Exception("Parent comment not found") }
        reply.parentComment = parentComment
        reply.article = parentComment.article
        return toCommentDTO(commentRepository.save(reply))
    }

    fun likeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.likes += 1
        return toCommentDTO(commentRepository.save(comment))
    }

    fun dislikeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.dislikes += 1
        return toCommentDTO(commentRepository.save(comment))
    }

    @Transactional
    fun editComment(commentId: Long, newContent: String): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.content = newContent
        return toCommentDTO(commentRepository.save(comment))
    }

    @Transactional
    fun deleteComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.content = "This comment has been deleted."
        comment.deleted = true
        return toCommentDTO(commentRepository.save(comment))
    }

    private fun toCommentDTO(comment: CommentDO): CommentDTO {
        return CommentDTO(
            id = comment.id,
            articleId = comment.article.id,
            parentComment = comment.parentComment?.let { ParentCommentDTO(it.id) },
            replies = comment.replies.filter { !it.deleted }.map { reply -> ReplyDTO(reply.id) }.toMutableList(),
            content = comment.content,
            likes = comment.likes,
            dislikes = comment.dislikes,
            deleted = comment.deleted
        )
    }

    private fun toCommentDO(comment: CommentDTO): CommentDO {
        return CommentDO(
            article = articleRepository.getReferenceById(comment.articleId),
            parentComment = comment.parentComment?.let { commentRepository.findById(it.id).orElseThrow { Exception("Parent comment not found") } },
            replies = comment.replies.map { reply -> commentRepository.findById(reply.id).orElseThrow { Exception("Reply not found") } }.toMutableList(),
            content = comment.content,
            likes = comment.likes,
            dislikes = comment.dislikes,
            deleted = comment.deleted
        )
    }
}
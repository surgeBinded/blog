package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ParentCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.ReplyDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.exceptions.CommentNotFoundException
import com.ropotdaniel.full_blog.exceptions.WrongArticleException
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

    @Transactional
    fun addComment(comment: CommentDTO): CommentDTO {
        val newCommentDO = toCommentDO(comment)

        if(newCommentDO.parentComment != null && newCommentDO.parentComment?.article?.id != newCommentDO.article.id) {
            throw WrongArticleException("Parent comment article must be the same as the comment article")
        }

        val createdComment = commentRepository.save(newCommentDO)
        return toCommentDTO(createdComment)
    }

    // TODO: perhaps this method is unnecessary since it does almost the same thing as addComment
    @Transactional
    fun addReply(parentCommentId: Long, reply: CommentDO): CommentDTO {
        val parentComment = commentRepository.findById(parentCommentId).orElseThrow { Exception("Parent comment not found") }

        if(parentComment.article.id == reply.article.id) {
            reply.parentComment = parentComment
            reply.article = parentComment.article
            return toCommentDTO(commentRepository.save(reply))
        } else {
            throw Exception("Reply article must be the same as the parent comment article")
        }
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
    fun editCommentContent(commentId: Long, newContent: String): CommentDTO {
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

    fun toCommentDTO(comment: CommentDO): CommentDTO {
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

    fun toCommentDO(comment: CommentDTO): CommentDO {
        val article = articleRepository.getReferenceById(comment.articleId)
        val parentComment = comment.parentComment?.let {
            commentRepository.findById(it.id).orElseThrow { CommentNotFoundException("Parent comment not found") }
        }
        val replies = comment.replies.map {
            commentRepository.findById(it.id).orElseThrow { CommentNotFoundException("Reply not found") }
        }.toMutableList()

        return CommentDO(
            article = article,
            parentComment = parentComment,
            replies = replies,
            content = comment.content,
            likes = comment.likes,
            dislikes = comment.dislikes,
            deleted = comment.deleted
        )
    }
}
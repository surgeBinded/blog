package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.exceptions.WrongArticleException
import com.ropotdaniel.full_blog.mapper.CommentMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

private const val COMMENT_NOT_FOUND = "Comment not found"

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val commentMapper: CommentMapper
) {

    fun getCommentsByArticleId(articleId: Long): List<CommentDTO> {
        return commentRepository.findByArticleId(articleId)
            .filter { !it.deleted }
            .map { comment -> commentMapper.toCommentDTO(comment) }
    }

    @Transactional
    fun addComment(comment: CommentDTO): CommentDTO {
        val newCommentDO = commentMapper.toCommentDO(comment)

        if (newCommentDO.parentComment != null && newCommentDO.parentComment?.article?.id != newCommentDO.article.id) {
            throw WrongArticleException("Parent comment article must be the same as the comment article")
        }

        val createdComment = commentRepository.save(newCommentDO)
        return commentMapper.toCommentDTO(createdComment)
    }

    // TODO: perhaps this method is unnecessary since it does almost the same thing as addComment
    @Transactional
    fun addReply(parentCommentId: Long, reply: CommentDO): CommentDTO {
        val parentComment =
            commentRepository.findById(parentCommentId).orElseThrow { Exception("Parent comment not found") }

        if (parentComment.article.id == reply.article.id) {
            reply.parentComment = parentComment
            reply.article = parentComment.article
            return commentMapper.toCommentDTO(commentRepository.save(reply))
        } else {
            throw Exception("Reply article must be the same as the parent comment article")
        }
    }

    fun likeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.likes += 1
        return commentMapper.toCommentDTO(commentRepository.save(comment))
    }

    fun dislikeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.dislikes += 1
        return commentMapper.toCommentDTO(commentRepository.save(comment))
    }

    @Transactional
    fun editCommentContent(commentId: Long, newContent: String): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.content = newContent
        return commentMapper.toCommentDTO(commentRepository.save(comment))
    }

    @Transactional
    fun deleteComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.content = "This comment has been deleted."
        comment.deleted = true
        return commentMapper.toCommentDTO(commentRepository.save(comment))
    }
}
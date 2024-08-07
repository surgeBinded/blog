package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.comment.UpdateCommentDTO
import com.ropotdaniel.full_blog.datatransferobject.response.CommentResponse
import com.ropotdaniel.full_blog.exceptions.WrongArticleException
import com.ropotdaniel.full_blog.mapper.CommentMapper
import com.ropotdaniel.full_blog.service.CommentService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

private const val COMMENT_NOT_FOUND = "Comment not found"

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val commentMapper: CommentMapper
): CommentService {

    private val logger = LoggerFactory.getLogger(ArticleServiceImpl::class.java)

    override fun getCommentsByArticleId(articleId: Long, pageNo: Int, pageSize: Int, sortBy:String, sortDir:String): CommentResponse {
        logger.info("Fetching comments by article id: $articleId")

        val sort = Sort.by(sortBy).let {
            if (sortDir.equals(Sort.Direction.ASC.name, ignoreCase = true)) it.ascending() else it.descending()
        }

        val pageable = PageRequest.of(pageNo, pageSize, sort)
        val comments = commentRepository.findByArticleId(articleId, pageable)
        val content = comments.content.map { comment -> commentMapper.toDTO(comment) }

        return CommentResponse(content, comments.number, comments.size, comments.totalElements, comments.totalPages, comments.isLast)
    }

    @Transactional
    override fun addComment(comment: CommentDTO): CommentDTO {
        val newCommentDO = commentMapper.toDO(comment)

        if (newCommentDO.parentComment != null && newCommentDO.parentComment.article.id != newCommentDO.article.id) {
            throw WrongArticleException("Parent comment article must be the same as the comment article")
        }

        val createdComment = commentRepository.save(newCommentDO)
        return commentMapper.toDTO(createdComment)
    }

    override fun likeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.likes += 1
        return commentMapper.toDTO(commentRepository.save(comment))
    }

    override fun dislikeComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.dislikes += 1
        return commentMapper.toDTO(commentRepository.save(comment))
    }

    @Transactional
    override fun editComment(commentId: Long, updateCommentDTO: UpdateCommentDTO): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }

        updateCommentDTO.content?.let { comment.content = it }
        updateCommentDTO.deleted?.let { comment.deleted = it }

        return commentMapper.toDTO(commentRepository.save(comment))
    }

    @Transactional
    override fun deleteComment(commentId: Long): CommentDTO {
        val comment = commentRepository.findById(commentId).orElseThrow { Exception(COMMENT_NOT_FOUND) }
        comment.content = "This comment has been deleted."
        comment.deleted = true
        return commentMapper.toDTO(commentRepository.save(comment))
    }
}
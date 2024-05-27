package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.service.CommentService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
@Validated
class CommentController(
    @Autowired private val commentService: CommentService
) {

    @GetMapping("/article/{articleId}")
    fun getCommentsByArticleId(@PathVariable articleId: Long): List<CommentDTO> {
        return commentService.getCommentsByArticleId(articleId)
    }

    @PostMapping("/")
    fun addComment(@Valid @RequestBody comment: CommentDTO): CommentDTO {
        return commentService.addComment(comment)
    }

    @PostMapping("/{commentId}/reply")
    fun addReply(@PathVariable commentId: Long, @Valid @RequestBody reply: CommentDO): CommentDTO {
        return commentService.addReply(commentId, reply)
    }

    @PatchMapping("/{commentId}/like")
    fun likeComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.likeComment(commentId)
    }

    @PatchMapping("/{commentId}/dislike")
    fun dislikeComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.dislikeComment(commentId)
    }

    @PutMapping("/{commentId}")
    fun editComment(@PathVariable commentId: Long, @RequestBody newContent: String): CommentDTO {
        return commentService.editComment(commentId, newContent)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.deleteComment(commentId)
    }
}
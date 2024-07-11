package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.service.CommentService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    @Autowired private val commentService: CommentService
) {

    @GetMapping("/article/{articleId}")
    fun getCommentsByArticleId(@PathVariable articleId: Long): List<CommentDTO> {
        return commentService.getCommentsByArticleId(articleId)
    }

    /* POST /api/v1/comments
    {
        "articleId": 1,
        "parentComment": {
            "id": 1
        },
        "content": "This is a comment"
    }
    * */
    @PostMapping("/")
    fun addComment(@Valid @RequestBody comment: CommentDTO): CommentDTO {
        return commentService.addComment(comment)
    }

    // TODO: perhaps this method is unnecessary since it does almost the same thing as addComment
    @PostMapping("/{commentId}/reply")
    fun addReply(@PathVariable commentId: Long, @Valid @RequestBody reply: CommentDO): CommentDTO {
        return commentService.addReply(commentId, reply)
    }

    /*
    * PATCH /api/v1/comments/1/like
    * */
    @PatchMapping("/{commentId}/like")
    fun likeComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.likeComment(commentId)
    }

    /*
    * PATCH /api/v1/comments/1/dislike
    * */
    @PatchMapping("/{commentId}/dislike")
    fun dislikeComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.dislikeComment(commentId)
    }

    /*
    * PATCH /api/v1/comments/1
    * {
    *  "newContent": "This is the new content"
    * }
     */
    @PatchMapping("/{commentId}")
    fun editCommentContent(@PathVariable commentId: Long, @RequestBody newContent: String): CommentDTO {
        return commentService.editCommentContent(commentId, newContent)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long): CommentDTO {
        return commentService.deleteComment(commentId)
    }
}
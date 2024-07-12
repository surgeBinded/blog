package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.CommentDTO
import com.ropotdaniel.full_blog.datatransferobject.response.CommentResponse
import com.ropotdaniel.full_blog.service.CommentService
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_PAGE_NUMBER
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_PAGE_SIZE
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_SORT_BY
import com.ropotdaniel.full_blog.util.Constants.Companion.DEFAULT_SORT_DIRECTION
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    @Autowired private val commentService: CommentService
) {
    /*
    * GET /api/v1/comments/article/1?pageNo=0&pageSize=10&sortBy=id&sortDir=asc
    *
    * Returns a paginated list of comments for an article
    *
    * articleId: the id of the article to fetch comments for
    * pageNo: the page number to fetch
    * pageSize: the number of comments to fetch per page
    * sortBy: the field to sort by
    * sortDir: the direction to sort by (asc or desc)
    *
    * Returns: CommentResponse
    *
    * Example response:
    * {
    *   "content": [
    *       {
    *           "id": 1,
    *           "articleId": 1,
    *           "parentComment": {
    *           "id": 1
    *       },
    *       "content": "This is a comment",
    *       "likes": 0,
    *       "dislikes": 0,
    *       "dateCreated": "2021-08-01T00:00:00Z"
    *       }
    *   ],
    *   "pageNo": 0,
    *   "pageSize": 10,
    *   "totalElements": 1,
    *   "totalPages": 1,
    *   "last": true
    * }
     */
    @GetMapping("/article/{articleId}")
    fun getCommentsByArticleId(@PathVariable articleId: Long,
                               @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER.toString(), required = false) pageNo: Int,
                               @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE.toString(), required = false) pageSize: Int,
                               @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) sortBy: String,
                               @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) sortDir: String): CommentResponse {
        return commentService.getCommentsByArticleId(articleId, pageNo, pageSize, sortBy, sortDir)
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
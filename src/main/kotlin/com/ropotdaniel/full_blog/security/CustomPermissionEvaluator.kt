package com.ropotdaniel.full_blog.security

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import com.ropotdaniel.full_blog.domainobject.Ownable
import com.ropotdaniel.full_blog.exceptions.ArticleNotFoundException
import com.ropotdaniel.full_blog.exceptions.CommentNotFoundException
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator(
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository
) : PermissionEvaluator {

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        return false
    }

    override fun hasPermission(
        authentication: Authentication,
        targetId: Serializable,
        targetType: String,
        permission: Any
    ): Boolean {
        if (permission !is String) return false

        val username = authentication.name

        // Check if the target type is ArticleDO or CommentDO and fetch the object by ID
        return when (targetType) {
            "ArticleDO" -> {
                val article = articleRepository.findById(targetId as Long)
                    .orElseThrow { ArticleNotFoundException("Article with id $targetId not found") }
                checkArticleOwnership(authentication, article, permission, username)
            }
            "CommentDO" -> {
                val comment = commentRepository.findById(targetId as Long)
                    .orElseThrow { CommentNotFoundException("Comment with id $targetId not found") }
                checkCommentOwnership(authentication, comment, permission, username)
            }
            else -> false
        }
    }

    // Helper function to check ownership for articles
    private fun checkArticleOwnership(
        authentication: Authentication,
        article: ArticleDO,
        permission: String,
        username: String
    ): Boolean {
        return checkAuthority(permission, authentication, article, username)
    }

    // Helper function to check ownership for comments
    private fun checkCommentOwnership(
        authentication: Authentication,
        comment: CommentDO,
        permission: String,
        username: String
    ): Boolean {
        return checkAuthority(permission, authentication, comment, username)
    }

    private fun checkAuthority(
        permission: String,
        authentication: Authentication,
        ownable: Ownable,
        username: String
    ) = when (permission) {
        "edit", "delete" -> {
            val isAdmin = authentication.authorities.any { it.authority == "ROLE_ADMIN" }
            val isAuthor = ownable.authorUsername == username // Compare authenticated username with comment author
            isAuthor || isAdmin // Allow if the user is either the author or an admin
        }
        else -> false
    }
}


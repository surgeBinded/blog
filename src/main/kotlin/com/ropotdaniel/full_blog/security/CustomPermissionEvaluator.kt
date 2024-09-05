package com.ropotdaniel.full_blog.security

import com.ropotdaniel.full_blog.domainobject.ArticleDO
import com.ropotdaniel.full_blog.domainobject.CommentDO
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    override fun hasPermission(
        authentication: Authentication,
        targetDomainObject: Any?,
        permission: Any
    ): Boolean {
        if (targetDomainObject == null || permission !is String) {
            return false
        }

        val username = authentication.name // Extract username from the current authentication

        return when (targetDomainObject) {
            is ArticleDO -> checkArticleOwnership(authentication, targetDomainObject, permission, username)
            is CommentDO -> checkCommentOwnership(authentication, targetDomainObject, permission, username)
            else -> false
        }
    }

    override fun hasPermission(
        authentication: Authentication,
        targetId: Serializable,
        targetType: String,
        permission: Any
    ): Boolean {
        return false // For ID-based checks (optional, not implemented here)
    }

    // Helper function to check ownership for articles
    private fun checkArticleOwnership(
        authentication: Authentication,
        article: ArticleDO,
        permission: String,
        username: String
    ): Boolean {
        return when (permission) {
            "edit" -> {
                val isAdmin = authentication.authorities.any { it.authority == "ROLE_ADMIN" }
                val isAuthor = article.author.username == username // Compare authenticated username with article author
                isAuthor || isAdmin // Allow if the user is either the author or an admin
            }
            else -> false
        }
    }

    // Helper function to check ownership for comments
    private fun checkCommentOwnership(
        authentication: Authentication,
        comment: CommentDO,
        permission: String,
        username: String
    ): Boolean {
        return when (permission) {
            "edit" -> {
                val isAdmin = authentication.authorities.any { it.authority == "ROLE_ADMIN" }
                val isAuthor = comment.author.username == username // Compare authenticated username with comment author
                isAuthor || isAdmin // Allow if the user is either the author or an admin
            }
            else -> false
        }
    }
}


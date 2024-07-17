package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.domainobject.UserDO
import org.springframework.stereotype.Component

@Component
object UserMapper{

    fun toUserDTO(user: UserDO): UserDTO {
        return UserDTO(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            articles = user.articles.map { article -> ArticleMapper.toArticleDTO(article) },
            comments = user.comments.map { comment -> CommentMapper.toCommentDTO(comment) }
        )
    }

    fun toUserDO(user: UserDTO): UserDO {
        return UserDO(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            password = user.password ?: throw IllegalArgumentException("Password must be provided"),
            articles = user.articles.map { article -> ArticleMapper.toArticleDO(article) },
            comments = user.comments.map { comment -> CommentMapper.toCommentDO(comment) }
        )
    }
}
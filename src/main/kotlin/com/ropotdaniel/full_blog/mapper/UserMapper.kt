package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.domainobject.UserDO
import org.springframework.stereotype.Component

@Component
object UserMapper{

    fun toDTO(user: UserDO): UserDTO {
        return UserDTO(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            articles = user.articles.map { article -> ArticleMapper.toDTO(article) },
            comments = user.comments.map { comment -> CommentMapper.toDTO(comment) }
        )
    }

    fun toDO(user: UserDTO): UserDO {
        return UserDO(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            password = user.password ?: throw IllegalArgumentException("Password must be provided"),
            articles = user.articles.map { article -> ArticleMapper.toDO(article) },
            comments = user.comments.map { comment -> CommentMapper.toDO(comment) }
        )
    }
}
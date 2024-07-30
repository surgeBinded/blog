package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.domainobject.UserDO
import org.springframework.stereotype.Component

@Component
object UserMapper{

    lateinit var commentRepository: CommentRepository
    lateinit var articleRepository: ArticleRepository

    fun toDTO(user: UserDO): UserDTO {
        return UserDTO(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            articles = user.articles.map { article -> article.id },
            comments = user.comments.map { comment -> comment.id },
            deleted = user.deleted,
            dateCreated = user.dateCreated
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
            articles = user.articles.map { id -> articleRepository.getReferenceById(id) },
            comments = user.comments.map { id -> commentRepository.getReferenceById(id) }
        )
    }
}
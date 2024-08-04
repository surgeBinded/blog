package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.user.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.user.UserDTO
import com.ropotdaniel.full_blog.domainobject.UserDO
import org.springframework.stereotype.Component

@Component
object UserMapper{

    lateinit var commentRepository: CommentRepository
    lateinit var articleRepository: ArticleRepository

    fun toDTO(user: UserDO): UserDTO {
        return UserDTO(
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

    fun toDO(user: CreateUserDTO): UserDO {
        return UserDO(
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.username,
            email = user.email,
            password = user.password,
            articles = emptyList(),
            comments = emptyList(),
            deleted = false
        )
    }
}
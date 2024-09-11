package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.domainobject.AuthorDO
import org.springframework.stereotype.Component

@Component
object AuthorMapper{

    lateinit var commentRepository: CommentRepository
    lateinit var articleRepository: ArticleRepository

    fun toDTO(author: AuthorDO): AuthorDTO {
        return AuthorDTO(
            id = author.id,
            firstName = author.firstName,
            lastName = author.lastName,
            username = author.username,
            email = author.email,
            articles = author.articles.map { article -> article.id },
            comments = author.comments.map { comment -> comment.id },
            deleted = author.deleted,
            dateCreated = author.dateCreated
        )
    }

    fun toDO(authorDTO: CreateAuthorDTO): AuthorDO {
        return AuthorDO(
            firstName = authorDTO.firstName,
            lastName = authorDTO.lastName,
            username = authorDTO.username,
            email = authorDTO.email,
            password = authorDTO.password,
            articles = emptyList(),
            comments = emptyList(),
            deleted = false
        )
    }
}
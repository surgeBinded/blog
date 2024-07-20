package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.UserRepository
import com.ropotdaniel.full_blog.datatransferobject.ArticleDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.springframework.stereotype.Component

@Component
object ArticleMapper {

    lateinit var userRepository: UserRepository

    fun toDTO(article: ArticleDO): ArticleDTO {
        return ArticleDTO(
            id = article.id,
            title = article.title,
            content = article.content,
            author = UserMapper.toDTO(article.user),
            bannerImageUrl = article.bannerImageUrl,
            dateCreated = article.dateCreated,
            comments = article.comments.map { comment -> CommentMapper.toDTO(comment) }
        )
    }

    fun toDO(article: ArticleDTO): ArticleDO {
        return ArticleDO(
            id = article.id,
            title = article.title,
            content = article.content,
            user = UserMapper.toDO(article.author),
            bannerImageUrl = article.bannerImageUrl,
            comments = article.comments.map { comment -> CommentMapper.toDO(comment) }
        )
    }

    fun toDTOList(articles: List<ArticleDO>): List<ArticleDTO> {
        return articles.stream().map(this::toDTO).toList()
    }

}
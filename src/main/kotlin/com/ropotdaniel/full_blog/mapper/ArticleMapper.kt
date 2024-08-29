package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.dataaccessobject.AuthorRepository
import com.ropotdaniel.full_blog.datatransferobject.article.CreateArticleDTO
import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.springframework.stereotype.Component

@Component
object ArticleMapper {

    lateinit var authorRepository: AuthorRepository

    fun toDTO(article: ArticleDO): ArticleDTO {
        return ArticleDTO(
            id = article.id,
            title = article.title,
            content = article.content,
            author = AuthorMapper.toDTO(article.author),
            bannerImageUrl = article.bannerImageUrl,
            dateUpdated = article.dateUpdated,
            dateCreated = article.dateCreated,
            comments = article.comments.map { comment -> CommentMapper.toDTO(comment) }
        )
    }

    fun toDO(article: CreateArticleDTO): ArticleDO {
        return ArticleDO(
            title = article.title,
            content = article.content,
            author = authorRepository.getReferenceById(article.authorId),
            bannerImageUrl = article.bannerImageUrl ?: ""
        )
    }

    fun toDTOList(articles: List<ArticleDO>): List<ArticleDTO> {
        return articles.stream().map(this::toDTO).toList()
    }

}
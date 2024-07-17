package com.ropotdaniel.full_blog.mapper

import com.ropotdaniel.full_blog.datatransferobject.ArticleDTO
import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.springframework.stereotype.Component

@Component
object ArticleMapper {

    fun toArticleDTO(article: ArticleDO): ArticleDTO {
        return ArticleDTO(
            id = article.id,
            title = article.title,
            content = article.content,
            author = UserMapper.toUserDTO(article.user),
            bannerImageUrl = article.bannerImageUrl,
            dateCreated = article.dateCreated,
            comments = article.comments.map { comment -> CommentMapper.toCommentDTO(comment) }
        )
    }

    fun toArticleDO(article: ArticleDTO): ArticleDO {
        return ArticleDO(
            id = article.id,
            title = article.title,
            content = article.content,
            user = UserMapper.toUserDO(article.author),
            bannerImageUrl = article.bannerImageUrl,
            comments = article.comments.map { comment -> CommentMapper.toCommentDO(comment) }
        )
    }

}
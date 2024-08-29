package com.ropotdaniel.full_blog.config

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.mapper.AuthorMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthorMapperConfig {

    @Bean
    fun authorMapper(articleRepository: ArticleRepository, commentRepository: CommentRepository): AuthorMapper {
        return AuthorMapper.apply {
            this.articleRepository = articleRepository
            this.commentRepository = commentRepository
        }
    }
}
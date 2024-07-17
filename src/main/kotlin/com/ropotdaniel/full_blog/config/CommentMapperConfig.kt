package com.ropotdaniel.full_blog.config

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.mapper.CommentMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommentMapperConfig {

    @Bean
    fun commentMapper(articleRepository: ArticleRepository, commentRepository: CommentRepository): CommentMapper {
        return CommentMapper.apply {
            this.articleRepository = articleRepository
            this.commentRepository = commentRepository
        }
    }

}
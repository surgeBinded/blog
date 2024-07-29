package com.ropotdaniel.full_blog.config

import com.ropotdaniel.full_blog.dataaccessobject.ArticleRepository
import com.ropotdaniel.full_blog.dataaccessobject.CommentRepository
import com.ropotdaniel.full_blog.mapper.UserMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserMapperConfig {

    @Bean
    fun userMapper(articleRepository: ArticleRepository, commentRepository: CommentRepository): UserMapper {
        return UserMapper.apply {
            this.articleRepository = articleRepository
            this.commentRepository = commentRepository
        }
    }
}
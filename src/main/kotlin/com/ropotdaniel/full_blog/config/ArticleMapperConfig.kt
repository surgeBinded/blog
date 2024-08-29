package com.ropotdaniel.full_blog.config

import com.ropotdaniel.full_blog.dataaccessobject.AuthorRepository
import com.ropotdaniel.full_blog.mapper.ArticleMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArticleMapperConfig {

    @Bean
    fun articleMapper(authorRepository: AuthorRepository): ArticleMapper {
        return ArticleMapper.apply {
            this.authorRepository = authorRepository
        }
    }

}
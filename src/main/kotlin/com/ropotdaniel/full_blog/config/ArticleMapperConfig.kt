package com.ropotdaniel.full_blog.config

import com.ropotdaniel.full_blog.dataaccessobject.UserRepository
import com.ropotdaniel.full_blog.mapper.ArticleMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArticleMapperConfig {

    @Bean
    fun articleMapper(userRepository: UserRepository): ArticleMapper {
        return ArticleMapper.apply {
            this.userRepository = userRepository
        }
    }

}
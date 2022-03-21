package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
}
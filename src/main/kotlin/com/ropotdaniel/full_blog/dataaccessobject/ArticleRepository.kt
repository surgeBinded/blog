package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.ArticleDO
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<ArticleDO, Long> {
}
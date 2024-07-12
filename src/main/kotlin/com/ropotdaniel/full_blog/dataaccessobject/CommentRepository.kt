package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.CommentDO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<CommentDO, Long> {
    fun findByArticleId(articleId: Long, pageable: Pageable): Page<CommentDO>
}
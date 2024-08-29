package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.AuthorDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<AuthorDO, Long> {

    fun save(authorDO: AuthorDO): AuthorDO
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

}

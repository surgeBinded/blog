package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.JwtSecretEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JwtSecretRepository : JpaRepository<JwtSecretEntity, Long> {
    fun findFirstByOrderByIdAsc(): JwtSecretEntity?
}
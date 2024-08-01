package com.ropotdaniel.full_blog.dataaccessobject

import com.ropotdaniel.full_blog.domainobject.UserDO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserDO, Long> {

    fun save(userDO: UserDO): UserDO
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

}

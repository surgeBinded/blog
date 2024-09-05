package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*

/**
 * Represents a role in the system, such as ROLE_USER or ROLE_ADMIN.
 *
 * Each role can be assigned to multiple authors, and each author can have multiple roles.
 * This is part of a many-to-many relationship with the AuthorDO class.
 *
 * Example roles: ROLE_USER, ROLE_ADMIN
 */
@Entity
@Table(name = "roles")
data class RoleDO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String,

    /**
     * The authors associated with this role. This is part of a many-to-many relationship
     * with AuthorDO, meaning multiple authors can have the same role, and an author
     * can have multiple roles.
     */
    @ManyToMany(mappedBy = "roles")
    val authors: Set<AuthorDO> = mutableSetOf()
)
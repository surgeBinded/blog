package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*

@Entity
@Table(name = "jwt_secret")
data class JwtSecretEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "secret_key", nullable = false)
    val secretKey: String
)

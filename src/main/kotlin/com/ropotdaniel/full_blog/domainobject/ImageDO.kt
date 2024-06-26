package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*

@Entity
@Table(name = "image")
data class ImageDO(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var url: String
)
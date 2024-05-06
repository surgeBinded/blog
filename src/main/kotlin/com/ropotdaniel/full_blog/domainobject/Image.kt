package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*

@Entity
@Table(name = "image")
class Image(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var url: String
)
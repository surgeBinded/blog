package com.ropotdaniel.full_blog.domainobject

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.Nullable
import java.time.ZonedDateTime
import jakarta.persistence.*

@Entity
@Table(name = "article")
class ArticleDO(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
                @Column(nullable = false) var title: String,
                @Lob @Column(nullable = false) var content: String,
                @Nullable @Lob var bannerImageUrl: String,
                @Column(nullable = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) var dateCreated: ZonedDateTime = ZonedDateTime.now()
)
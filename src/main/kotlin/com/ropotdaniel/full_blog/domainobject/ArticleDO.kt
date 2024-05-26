package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.Nullable
import java.time.ZonedDateTime

@Entity
@Table(name = "article")
class ArticleDO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(
        max = 100,
        message = "Title cannot exceed 100 characters"
    ) var title: String,

    @Lob
    @Column(nullable = false)
    var content: String,

    @Nullable
    @Lob
    var bannerImageUrl: String,

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var dateCreated: ZonedDateTime = ZonedDateTime.now()
)
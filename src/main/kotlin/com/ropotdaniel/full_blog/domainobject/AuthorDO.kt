package com.ropotdaniel.full_blog.domainobject

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime

@Entity
@Table(name = "authors")
data class AuthorDO(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    val email: String,

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val articles: List<ArticleDO> = mutableListOf(),

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: List<CommentDO> = mutableListOf(),

    @Column(nullable = false)
    var deleted: Boolean = false
) {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateCreated: ZonedDateTime = ZonedDateTime.now()
}

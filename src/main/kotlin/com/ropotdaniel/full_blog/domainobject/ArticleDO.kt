package com.ropotdaniel.full_blog.domainobject

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.Nullable
import java.time.ZonedDateTime

@Entity
@Table(name = "article")
data class ArticleDO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

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
    @Column(name = "banner_image_url")
    var bannerImageUrl: String,

    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: List<CommentDO> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: AuthorDO,

    @Column(name = "date_updated")
    var dateUpdated: ZonedDateTime = ZonedDateTime.now()
): Ownable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateCreated: ZonedDateTime = ZonedDateTime.now()

    override val authorUsername: String
        get() = author.username
}
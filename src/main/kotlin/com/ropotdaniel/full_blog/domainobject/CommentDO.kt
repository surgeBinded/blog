package com.ropotdaniel.full_blog.domainobject

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime

@Entity
@Table(name = "comment")
data class CommentDO(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: ArticleDO,

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    val parentComment: CommentDO? = null,

    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL])
    val replies: List<CommentDO> = mutableListOf(),

    @Column(nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserDO,

    @Column(nullable = false)
    var likes: Int = 0,

    @Column(nullable = false)
    var dislikes: Int = 0,

    @Column(nullable = false)
    var deleted: Boolean = false,

    @Column(name = "date_updated")
    var dateUpdated: ZonedDateTime = ZonedDateTime.now()
) {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateCreated: ZonedDateTime = ZonedDateTime.now()
}
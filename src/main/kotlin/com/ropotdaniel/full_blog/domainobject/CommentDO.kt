package com.ropotdaniel.full_blog.domainobject

import jakarta.persistence.*

@Entity
@Table(name = "comment")
data class CommentDO(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne @JoinColumn(name = "article_id")
    var article: ArticleDO,

    @ManyToOne @JoinColumn(name = "parent_comment_id")
    var parentComment: CommentDO? = null,

    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL])
    val replies: List<CommentDO> = mutableListOf(),

    var content: String,

//    @ManyToOne @JoinColumn(name = "user_id")
//    var user: UserDO,

    var likes: Int = 0,
    var dislikes: Int = 0,

    var deleted: Boolean = false
)
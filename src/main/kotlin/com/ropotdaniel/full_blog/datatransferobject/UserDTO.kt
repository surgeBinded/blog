package com.ropotdaniel.full_blog.datatransferobject

data class UserDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String? = null,
    val articles: List<ArticleDTO>,
    val comments: List<CommentDTO>
)

package com.ropotdaniel.full_blog.datatransferobject

import com.fasterxml.jackson.annotation.JsonInclude

data class UserDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val password: String? = null,
    val articles: List<Long>,
    val comments: List<Long>
)

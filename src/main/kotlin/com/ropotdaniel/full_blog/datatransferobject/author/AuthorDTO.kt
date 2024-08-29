package com.ropotdaniel.full_blog.datatransferobject.author

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.ZonedDateTime

data class AuthorDTO(
    var firstName: String,
    var lastName: String,
    var username: String,
    var email: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var password: String? = null,
    var articles: List<Long>,
    var comments: List<Long>,
    var deleted: Boolean,
    var dateCreated: ZonedDateTime = ZonedDateTime.now()
)

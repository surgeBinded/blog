package com.ropotdaniel.full_blog.datatransferobject.author

data class UpdateAuthorDTO(
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var deleted: Boolean? = null
)
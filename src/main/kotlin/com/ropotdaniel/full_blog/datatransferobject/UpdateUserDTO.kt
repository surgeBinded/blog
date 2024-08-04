package com.ropotdaniel.full_blog.datatransferobject

data class UpdateUserDTO(
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var deleted: Boolean? = null
)
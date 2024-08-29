package com.ropotdaniel.full_blog.datatransferobject.author

import jakarta.validation.constraints.NotBlank

data class CreateAuthorDTO(
    @field:NotBlank(message = "First name must not be blank")
    var firstName: String,

    @field:NotBlank(message = "Last name must not be blank")
    var lastName: String,

    @field:NotBlank(message = "Username must not be blank")
    var username: String,

    @field:NotBlank(message = "Email must not be blank")
    var email: String,

    @field:NotBlank(message = "Password must not be blank")
    var password: String
)

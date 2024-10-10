package com.ropotdaniel.full_blog.datatransferobject.author

import jakarta.validation.constraints.NotEmpty

class UpdateAuthorPassword (
    @NotEmpty
    var oldPassword: String,

    @NotEmpty
    var newPassword: String,

    @NotEmpty
    var repeatNewPassword: String
)
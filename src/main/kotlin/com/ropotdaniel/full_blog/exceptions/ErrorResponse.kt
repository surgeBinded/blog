package com.ropotdaniel.full_blog.exceptions

data class ErrorResponse(
    val status: Int,
    val message: String,
    val details: List<String>
)
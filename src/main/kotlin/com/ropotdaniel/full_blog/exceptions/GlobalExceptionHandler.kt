package com.ropotdaniel.full_blog.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(WrongArticleException::class)
    fun handleCustomException(ex: WrongArticleException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message)
    }

    @ExceptionHandler(AuthorAlreadyExistsException::class)
    fun handleAuthorAlreadyExistsException(ex: AuthorAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message ?: "Author already exists",
            details = listOf(ex.message ?: "Author already exists")
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.allErrors.map { error ->
            val fieldError = error as FieldError
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            details = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorNotFoundException::class)
    fun handleAuthorNotFoundException(ex: AuthorNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(ArticleNotFoundException::class)
    fun handleAuthorNotFoundException(ex: ArticleNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(OldPasswordIncorrectException::class)
    fun handleOldPasswordIncorrectException(ex: OldPasswordIncorrectException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.message)
    }

    @ExceptionHandler(NewPasswordsNotMatchingException::class)
    fun handleNewPasswordsNotMatchingException(ex: NewPasswordsNotMatchingException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.message)
    }
}
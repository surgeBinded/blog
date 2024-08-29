package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.service.AuthorService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthorController @Autowired constructor(private val authorService: AuthorService) {

    @GetMapping("/authors/{id}")
    fun getAuthor(@PathVariable id: Long): AuthorDTO = authorService.getAuthor(id)

    @PostMapping("/authors/register")
    fun registerAuthor(@Valid @RequestBody createAuthorDTO: CreateAuthorDTO): AuthorDTO =
        authorService.registerAuthor(createAuthorDTO)

    @PatchMapping("/authors/{id}")
    fun updateAuthor(@PathVariable id: Long, @RequestBody updateAuthorDTO: UpdateAuthorDTO): AuthorDTO =
        authorService.updateAuthor(id, updateAuthorDTO)

    @DeleteMapping("/authors/{id}")
    fun deleteAuthor(@PathVariable id: Long) = authorService.deleteAuthor(id)
}
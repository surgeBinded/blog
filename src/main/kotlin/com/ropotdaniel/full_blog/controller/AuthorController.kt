package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.service.AuthorService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthorController @Autowired constructor(private val authorService: AuthorService) {

    @GetMapping("/authors/{id}")
    fun getAuthor(@PathVariable id: Long): ResponseEntity<AuthorDTO>
        = ResponseEntity.ok(authorService.getAuthor(id))

    @PostMapping("/authors/register")
    fun registerAuthor(@Valid @RequestBody createAuthorDTO: CreateAuthorDTO): ResponseEntity<AuthorDTO>
        = ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authorService.registerAuthor(createAuthorDTO))

    /*
       TODO: only the admin and the author themselves should be able to execute the update here
        perhaps consider creating a dedicated custom permission evaluator for authors
     */
    @PatchMapping("/authors/{id}")
    fun updateAuthor(@PathVariable id: Long, @RequestBody updateAuthorDTO: UpdateAuthorDTO): ResponseEntity<AuthorDTO>
        = ResponseEntity.ok(authorService.updateAuthor(id, updateAuthorDTO))

    /*
    * Create an update password endpoint
    * */

    @DeleteMapping("/authors/{id}")
    fun deleteAuthor(@PathVariable id: Long): ResponseEntity<String> {
        authorService.deleteAuthor(id)
        return ResponseEntity.ok("Author successfully deleted.");
    }
}
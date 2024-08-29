package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.AuthorRepository
import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.exceptions.AuthorAlreadyExistsException
import com.ropotdaniel.full_blog.exceptions.AuthorNotFoundException
import com.ropotdaniel.full_blog.mapper.AuthorMapper
import com.ropotdaniel.full_blog.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl @Autowired constructor(private val authorRepository: AuthorRepository) : AuthorService {

    override fun getAuthor(id: Long): AuthorDTO {
        return AuthorMapper.toDTO(authorRepository.findById(id).get())
    }

    override fun registerAuthor(createAuthorDTO: CreateAuthorDTO): AuthorDTO {
        val existingFields = listOfNotNull(
            authorRepository.existsByUsername(createAuthorDTO.username)
                .takeIf { it }?.let { "Username '${createAuthorDTO.username}'" },
            authorRepository.existsByEmail(createAuthorDTO.email)
                .takeIf { it }?.let { "Email '${createAuthorDTO.email}'" }
        )

        if (existingFields.isNotEmpty()) {
            val message = existingFields.joinToString(" and ") { "$it already exists" }
            throw AuthorAlreadyExistsException(message)
        }

        return AuthorMapper.toDTO(authorRepository.save(AuthorMapper.toDO(createAuthorDTO)))
    }

    override fun updateAuthor(id: Long, updateAuthorDTO: UpdateAuthorDTO): AuthorDTO {
        val repoAuthor = authorRepository.findById(id).orElseThrow { AuthorNotFoundException("author not found with id = $id") }

        updateAuthorDTO.firstName?.let { repoAuthor.firstName = it }
        updateAuthorDTO.lastName?.let { repoAuthor.lastName = it }
        updateAuthorDTO.password?.let { repoAuthor.password = it }
        updateAuthorDTO.deleted?.let { repoAuthor.deleted = it }

        val updatedAuthor = authorRepository.save(repoAuthor)

        return AuthorMapper.toDTO(updatedAuthor)
    }

    override fun deleteAuthor(id: Long) {
        authorRepository.deleteById(id)
    }
}
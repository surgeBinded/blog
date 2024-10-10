package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.AuthorRepository
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorPassword
import com.ropotdaniel.full_blog.domainobject.AuthorDO
import com.ropotdaniel.full_blog.exceptions.AuthorAlreadyExistsException
import com.ropotdaniel.full_blog.exceptions.AuthorNotFoundException
import com.ropotdaniel.full_blog.exceptions.OldPasswordIncorrectException
import com.ropotdaniel.full_blog.mapper.AuthorMapper
import com.ropotdaniel.full_blog.service.AuthorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl @Autowired constructor(
    private val authorRepository: AuthorRepository,
    private val passwordEncoder: PasswordEncoder
) : AuthorService {

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
        val repoAuthor = getRepoAuthor(id)

        updateAuthorDTO.firstName?.let { repoAuthor.firstName = it }
        updateAuthorDTO.lastName?.let { repoAuthor.lastName = it }

        val updatedAuthor = authorRepository.save(repoAuthor)

        return AuthorMapper.toDTO(updatedAuthor)
    }

    // TODO: finish up
    override fun changePassword(id: Long, updateAuthorPassword: UpdateAuthorPassword) {
        val repoAuthor = getRepoAuthor(id)

        print(repoAuthor.password + " " + updateAuthorPassword.oldPassword)
        print(passwordEncoder.matches(repoAuthor.password, updateAuthorPassword.oldPassword))

        if (!passwordEncoder.matches(repoAuthor.password, updateAuthorPassword.oldPassword)){
            throw OldPasswordIncorrectException("The old password is not the correct one")
        }

        repoAuthor.password = passwordEncoder.encode(updateAuthorPassword.newPassword)
        authorRepository.save(repoAuthor)
    }

    override fun deleteAuthor(id: Long) {
        authorRepository.deleteById(id)
    }

    private fun getRepoAuthor(id: Long): AuthorDO =
        authorRepository.findById(id).orElseThrow { AuthorNotFoundException("author not found with id = $id") }
}
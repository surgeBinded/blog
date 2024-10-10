package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorPassword

interface AuthorService {
    fun getAuthor(id: Long): AuthorDTO
    fun registerAuthor(createAuthorDTO: CreateAuthorDTO): AuthorDTO
    fun updateAuthor(id: Long, updateAuthorDTO: UpdateAuthorDTO): AuthorDTO
    fun changePassword(id: Long, updateAuthorPassword: UpdateAuthorPassword)
    fun deleteAuthor(id: Long)
}
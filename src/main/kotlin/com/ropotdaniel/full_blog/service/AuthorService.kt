package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.author.CreateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.UpdateAuthorDTO
import com.ropotdaniel.full_blog.datatransferobject.author.AuthorDTO

interface AuthorService {
    fun getAuthor(id: Long): AuthorDTO
    fun registerAuthor(createAuthorDTO: CreateAuthorDTO): AuthorDTO
    fun updateAuthor(id: Long, updateAuthorDTO: UpdateAuthorDTO): AuthorDTO
    fun deleteAuthor(id: Long)
}
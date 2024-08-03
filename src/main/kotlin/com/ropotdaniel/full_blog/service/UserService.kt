package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO

interface UserService {
    fun getUser(id: Long): UserDTO
    fun registerUser(createUserDTO: CreateUserDTO): UserDTO
    fun updateUser(id: Long, createUserDTO: CreateUserDTO): UserDTO
    fun deleteUser(id: Long)
}
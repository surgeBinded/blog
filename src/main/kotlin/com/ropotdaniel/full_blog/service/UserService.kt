package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UpdateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO

interface UserService {
    fun getUser(id: Long): UserDTO
    fun registerUser(createUserDTO: CreateUserDTO): UserDTO
    fun updateUser(id: Long, updateUserDTO: UpdateUserDTO): UserDTO
    fun deleteUser(id: Long)
}
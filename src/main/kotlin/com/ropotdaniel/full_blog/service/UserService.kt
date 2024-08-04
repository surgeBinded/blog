package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.user.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.user.UpdateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.user.UserDTO

interface UserService {
    fun getUser(id: Long): UserDTO
    fun registerUser(createUserDTO: CreateUserDTO): UserDTO
    fun updateUser(id: Long, updateUserDTO: UpdateUserDTO): UserDTO
    fun deleteUser(id: Long)
}
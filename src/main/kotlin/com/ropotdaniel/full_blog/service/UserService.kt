package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.datatransferobject.UserCreateDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO

interface UserService {
    fun getUser(id: Long): UserDTO
    fun registerUser(userCreateDTO: UserCreateDTO): UserDTO
    fun updateUser(id: Long, userDTO: UserDTO): UserDTO
    fun deleteUser(id: Long)
}
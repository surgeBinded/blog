package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UpdateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController @Autowired constructor(private val userService: UserService) {

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Long): UserDTO = userService.getUser(id)

    @PostMapping("/users/register")
    fun registerUser(@Valid @RequestBody createUserDTO: CreateUserDTO): UserDTO =
        userService.registerUser(createUserDTO)

    @PatchMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updateUserDTO: UpdateUserDTO): UserDTO =
        userService.updateUser(id, updateUserDTO)

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}
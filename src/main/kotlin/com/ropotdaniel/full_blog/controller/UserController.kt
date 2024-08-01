package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.datatransferobject.UserCreateDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController @Autowired constructor(private val userService: UserService) {

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Long): UserDTO
        = userService.getUser(id)

    @PostMapping("/users/register")
    fun registerUser(@Valid @RequestBody userCreateDTO: UserCreateDTO): UserDTO
        = userService.registerUser(userCreateDTO)

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, userDTO: UserDTO): UserDTO
        = userService.updateUser(id, userDTO)

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long)
        = userService.deleteUser(id)


}
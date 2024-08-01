package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.UserRepository
import com.ropotdaniel.full_blog.datatransferobject.UserCreateDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.exceptions.UserAlreadyExistsException
import com.ropotdaniel.full_blog.mapper.UserMapper
import com.ropotdaniel.full_blog.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun getUser(id: Long): UserDTO {
        return UserMapper.toDTO(userRepository.findById(id).get())
    }

    override fun registerUser(userCreateDTO: UserCreateDTO): UserDTO {
        val existingFields = listOfNotNull(
            userRepository.existsByUsername(userCreateDTO.username)
                .takeIf { it }?.let { "Username '${userCreateDTO.username}'" },
            userRepository.existsByEmail(userCreateDTO.email)
                .takeIf { it }?.let { "Email '${userCreateDTO.email}'" }
        )

        if (existingFields.isNotEmpty()) {
            val message = existingFields.joinToString(" and ") { "$it already exists" }
            throw UserAlreadyExistsException(message)
        }

        return UserMapper.toDTO(userRepository.save(UserMapper.toDO(userCreateDTO)))
    }

    override fun updateUser(id: Long, userDTO: UserDTO): UserDTO {
        if (!userRepository.findById(id).isPresent) {
              throw Exception("User not found with id = $id")
        }

        val user = UserMapper.toDO(userDTO)
        val updatedUser = userRepository.save(user)

        return UserMapper.toDTO(updatedUser)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
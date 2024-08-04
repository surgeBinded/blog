package com.ropotdaniel.full_blog.service.impl

import com.ropotdaniel.full_blog.dataaccessobject.UserRepository
import com.ropotdaniel.full_blog.datatransferobject.CreateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UpdateUserDTO
import com.ropotdaniel.full_blog.datatransferobject.UserDTO
import com.ropotdaniel.full_blog.exceptions.UserAlreadyExistsException
import com.ropotdaniel.full_blog.exceptions.UserNotFoundException
import com.ropotdaniel.full_blog.mapper.UserMapper
import com.ropotdaniel.full_blog.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun getUser(id: Long): UserDTO {
        return UserMapper.toDTO(userRepository.findById(id).get())
    }

    override fun registerUser(createUserDTO: CreateUserDTO): UserDTO {
        val existingFields = listOfNotNull(
            userRepository.existsByUsername(createUserDTO.username)
                .takeIf { it }?.let { "Username '${createUserDTO.username}'" },
            userRepository.existsByEmail(createUserDTO.email)
                .takeIf { it }?.let { "Email '${createUserDTO.email}'" }
        )

        if (existingFields.isNotEmpty()) {
            val message = existingFields.joinToString(" and ") { "$it already exists" }
            throw UserAlreadyExistsException(message)
        }

        return UserMapper.toDTO(userRepository.save(UserMapper.toDO(createUserDTO)))
    }

    override fun updateUser(id: Long, updateUserDTO: UpdateUserDTO): UserDTO {
        val repoUser = userRepository.findById(id).orElseThrow { UserNotFoundException("User not found with id = $id") }

        updateUserDTO.firstName?.let { repoUser.firstName = it }
        updateUserDTO.lastName?.let { repoUser.lastName = it }
        updateUserDTO.password?.let { repoUser.password = it }
        updateUserDTO.deleted?.let { repoUser.deleted = it }

        val updatedUser = userRepository.save(repoUser)

        return UserMapper.toDTO(updatedUser)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
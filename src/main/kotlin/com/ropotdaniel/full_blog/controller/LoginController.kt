package com.ropotdaniel.full_blog.controller

import com.ropotdaniel.full_blog.security.JwtTokenUtil
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

data class JwtResponse(val token: String)
data class LoginRequest(val username: String, val password: String)

@RestController
@RequestMapping("/api/v1/auth")
class LoginController @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): JwtResponse {
        return try {
            val authenticationToken = UsernamePasswordAuthenticationToken(request.username, request.password)
            val authentication = authenticationManager.authenticate(authenticationToken)

            // Generate a JWT token for the authenticated user
            val jwtToken = jwtTokenUtil.generateToken(authentication.name, mapOf())

            JwtResponse(jwtToken) // Return the JWT token in the response
        } catch (e: AuthenticationException) {
            throw RuntimeException("Invalid username or password", e) // Add logging or more specific error message
        }
    }
}
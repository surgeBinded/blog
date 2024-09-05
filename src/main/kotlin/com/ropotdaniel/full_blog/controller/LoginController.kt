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
    @Throws(AuthenticationException::class)
    fun login(@Valid @RequestBody request: LoginRequest): JwtResponse {
        val authenticationToken = UsernamePasswordAuthenticationToken(request.username, request.password)

        // Authenticate the user (this will throw an exception if the credentials are invalid)
        val authentication = authenticationManager.authenticate(authenticationToken)

        // Generate a JWT token for the authenticated user
        val jwtToken = jwtTokenUtil.generateToken(authentication.name, mapOf())

        return JwtResponse(jwtToken) // Return the JWT token in the response
    }
}
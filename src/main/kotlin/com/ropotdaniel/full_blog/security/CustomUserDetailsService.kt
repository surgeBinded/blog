package com.ropotdaniel.full_blog.security

import com.ropotdaniel.full_blog.dataaccessobject.AuthorRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val authorRepository: AuthorRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val author = authorRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        val authorities: Set<GrantedAuthority> = author.roles
            .map { role -> SimpleGrantedAuthority(role.name) }
            .toSet()

        return org.springframework.security.core.userdetails.User(
            author.username,
            author.password,
            authorities
        )
    }
}
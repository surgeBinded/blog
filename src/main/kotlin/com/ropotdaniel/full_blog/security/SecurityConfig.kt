package com.ropotdaniel.full_blog.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter

@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter,
    private val customPermissionEvaluator: CustomPermissionEvaluator,
    private val customUserDetailsService: CustomUserDetailsService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers { headers ->
            headers
                .contentSecurityPolicy { csp ->
                    csp.policyDirectives("default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self'; frame-ancestors 'self'")
                }
                .frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
                .httpStrictTransportSecurity { hsts ->
                    hsts
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000)
                        .preload(true)
                }
                .referrerPolicy { referrerPolicy ->
                    referrerPolicy.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)
                }
                .permissionsPolicy { permissions ->
                    permissions.policy("geolocation=(), camera=(), microphone=()")
                }
        }
        http.csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/auth/**").permitAll() // Allow login and register endpoints
                    .anyRequest().authenticated() // All other endpoints require authentication
            }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun configureAuthenticationManager(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun methodSecurityExpressionHandler(): DefaultMethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator)
        return expressionHandler
    }
}
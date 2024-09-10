package com.ropotdaniel.full_blog.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
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

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter,
    private val customPermissionEvaluator: CustomPermissionEvaluator,
    private val customUserDetailsService: CustomUserDetailsService,
    private val env: Environment
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Apply profile-specific settings
        if (env.activeProfiles.contains("dev")) {
            // Development-specific settings for H2 console
            http.headers { headers ->
                headers
                    .contentSecurityPolicy { csp ->
                        // Allow 'unsafe-inline' for H2 console in development only
                        csp.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; object-src 'none'; style-src 'self'; frame-ancestors 'self'")
                    }
                    .frameOptions { frameOptions ->
                        frameOptions.sameOrigin() // Allow frames for H2 console
                    }
            }
            http.csrf { it.disable() } // Disable CSRF in development

            http.authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console in dev
            }
        } else if (env.activeProfiles.contains("prd")) {
            // Production-specific settings
            http.headers { headers ->
                headers
                    .contentSecurityPolicy { csp ->
                        // Strict CSP, no unsafe-inline in production
                        csp.policyDirectives("default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self'; frame-ancestors 'self'")
                    }
                    .frameOptions { frameOptions ->
                        frameOptions.sameOrigin() // Ensure frames work in prod (if needed)
                    }
            }
        }

        // Common authorization rules for both profiles
        http.authorizeHttpRequests { auth ->
            auth
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll() // Allow GET requests in both dev and prod
                .requestMatchers("/api/v1/auth/**").permitAll() // Allow login and register endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
        }

        // Common JWT filter for both profiles
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    // Provide the AuthenticationManager and configure it with the CustomUserDetailsService and PasswordEncoder
    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration,
        authenticationManagerBuilder: AuthenticationManagerBuilder
    ): AuthenticationManager {
        // Configure the authentication manager with your custom UserDetailsService and PasswordEncoder
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())

        // Return the authentication manager
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun methodSecurityExpressionHandler(): DefaultMethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator)
        return expressionHandler
    }
}
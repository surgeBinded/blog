package com.ropotdaniel.full_blog.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
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
@Configuration
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter,
    private val customPermissionEvaluator: CustomPermissionEvaluator,
    private val customUserDetailsService: CustomUserDetailsService
) {

    @Bean
    @Profile("dev")
    fun devSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers { headers ->
            headers
                .contentSecurityPolicy { csp ->
                    // Allow 'unsafe-inline' for H2 console in development only
                    csp.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; object-src 'none'; style-src 'self'; frame-ancestors 'self'")
                }
                .frameOptions { frameOptions ->
                    frameOptions.sameOrigin() // Required for H2 console
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
        http.csrf { it.disable() } // Disable CSRF for H2 console in development

        http.authorizeHttpRequests { auth ->
            auth
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console in dev
                .requestMatchers("/api/v1/auth/**").permitAll() // Allow login and register endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
        }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    // Production Profile: Block H2 console, enable CSRF, strict CSP
    @Bean
    @Profile("prd")
    fun prodSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.headers { headers ->
            headers
                .contentSecurityPolicy { csp ->
                    // Strict CSP, no unsafe-inline in production
                    csp.policyDirectives("default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self'; frame-ancestors 'self'")
                }
                .frameOptions { frameOptions ->
                    frameOptions.sameOrigin() // Ensure frames work in prod (if needed)
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

        http.authorizeHttpRequests { auth ->
            auth
                .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                .requestMatchers("/api/v1/auth/**").permitAll() // Allow login and register endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
        }
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

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
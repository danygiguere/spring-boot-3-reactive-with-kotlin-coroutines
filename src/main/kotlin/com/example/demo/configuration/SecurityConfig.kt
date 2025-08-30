package com.example.demo.configuration

import com.example.demo.security.SecurityContextRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@EnableWebFluxSecurity
@Configuration
class SecurityConfig(private val securityContextRepository: SecurityContextRepository) {

    @Value("\${app.cors-allowed-origins}")
    private val corsAllowedOrigins: String? = null

    companion object {
        private val PUBLIC_PATHS = arrayOf(
            "/actuator/health/**", "/api/demo/**", "/api/users/**", "/api/images/**", "/api/profile/**",
           "/api/register", "/api/login", "/api/login/cookie", "/api/refresh-token", "/api/refresh-token/cookie"
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val corsConfig = CorsConfiguration().apply {
            allowedOrigins = corsAllowedOrigins?.split(",")?.map { it.trim() } ?: listOf("*")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .cors { }
            .exceptionHandling {
                it.authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
            }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .securityContextRepository(securityContextRepository)
            .logout { it.disable() }
            .authorizeExchange {
                it.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .pathMatchers(HttpMethod.GET, "/actuator/health/**").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                    .pathMatchers(*PUBLIC_PATHS).permitAll()
                    .anyExchange().authenticated()
            }
            .build()
    }

}

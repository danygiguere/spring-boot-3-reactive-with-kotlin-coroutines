package com.example.demo.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import com.example.demo.security.SecurityContextRepository
import org.springframework.http.HttpMethod
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@EnableWebFluxSecurity
@Configuration
class SecurityConfig(private val securityContextRepository: SecurityContextRepository) {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("http://localhost:4200")
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)
        return CorsWebFilter(source)
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .cors { }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .securityContextRepository(securityContextRepository)
            .authorizeExchange {
                it.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .pathMatchers(HttpMethod.GET, "/posts/**").permitAll()
                    .pathMatchers("/demo/**").permitAll()
                    .pathMatchers("/users/**").permitAll()
                    .pathMatchers("/images/**").permitAll()
                    .pathMatchers("/profile/**").permitAll()
                    .pathMatchers("/status/check").permitAll()
                    .pathMatchers("/register").permitAll()
                    .pathMatchers("/login").permitAll()
                    .anyExchange().authenticated()
            }
            .build()
    }
}
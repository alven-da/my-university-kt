package com.myuniversity.apigateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity // Note: Gateway uses WebFlux, not WebMvc
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() } // Disable CSRF for API endpoints
            .authorizeExchange { exchange ->
                exchange.pathMatchers("/auth/**").permitAll() // Allow auth routes
                exchange.anyExchange().authenticated() // Protect everything else
            }
            .build()
    }
}
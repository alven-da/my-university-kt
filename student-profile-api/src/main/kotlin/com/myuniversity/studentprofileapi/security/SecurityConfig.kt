package com.myuniversity.studentprofileapi.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Standard for stateless APIs
            .authorizeHttpRequests { auth ->
                // All profile endpoints require a valid token
                auth.requestMatchers("/profile/**").authenticated()
                auth.anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                // Tells Spring to treat this app as a Resource Server using JWTs
                oauth2.jwt(Customizer.withDefaults())
            }

        return http.build()
    }
}
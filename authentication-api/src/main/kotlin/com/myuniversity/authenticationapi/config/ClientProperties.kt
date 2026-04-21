package com.myuniversity.authenticationapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.initial-client")
data class ClientProperties(
    val id: String,
    val secret: String,
    val redirectUri: String,
    val scopes: List<String>
)
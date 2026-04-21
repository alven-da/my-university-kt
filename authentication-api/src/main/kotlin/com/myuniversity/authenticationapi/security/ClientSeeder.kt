package com.myuniversity.authenticationapi.security

import com.myuniversity.authenticationapi.config.ClientProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@EnableConfigurationProperties(ClientProperties::class)
class ClientSeeder(
    private val registeredClientRepository: RegisteredClientRepository,
    private val passwordEncoder: PasswordEncoder,
    private val properties: ClientProperties
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        if (registeredClientRepository.findByClientId(properties.id) == null) {
            logger.info("Seeding initial client: {}", properties.id)

            val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(properties.id)
                .clientSecret(passwordEncoder.encode(properties.secret)) // No {noop} prefix needed now
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri(properties.redirectUri)
                .apply{
                    properties.scopes.forEach { scope(it) }
                }
                .build()

            registeredClientRepository.save(registeredClient)

            logger.info("✅ Initial client seeded successfully.")
        } else {
            logger.debug("Initial client {} exists and is already registered", properties.id)
        }
    }

}
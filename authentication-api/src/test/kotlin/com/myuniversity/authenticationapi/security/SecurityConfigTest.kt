package com.myuniversity.authenticationapi.security

import com.myuniversity.authenticationapi.config.ClientProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertTrue

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties(ClientProperties::class)
class SecurityConfigTest(
    @Autowired val properties: ClientProperties
) {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `should expose OIDC discovery endpoint`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/.well-known/openid-configuration"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.issuer").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.jwks_uri").exists())
    }

    @Test
    fun `should load initial client properties from yaml`() {
        // This ensures the ${INITIAL_CLIENT_ID:test-client} logic is working
        assertEquals("test-client", properties.id)
        assertEquals("test-secret", properties.secret)
        assertTrue(properties.scopes.contains("openid"))
    }

    @Test
    fun `should encode and match passwords using BCrypt`() {
        val rawPassword = "test-secret"
        val encodedPassword = passwordEncoder.encode(rawPassword)

        // Should not be plain text
        assertNotNull(encodedPassword)
        assertNotEquals(rawPassword, encodedPassword)

        // Should start with BCrypt prefix standard
        assertTrue(encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$"))

        // Verify matching works
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
    }

}
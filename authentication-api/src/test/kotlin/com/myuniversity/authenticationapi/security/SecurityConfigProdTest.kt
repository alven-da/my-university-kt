package com.myuniversity.authenticationapi.security

import com.myuniversity.authenticationapi.config.ClientProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

// Simulating production-like environment
@SpringBootTest(properties = ["INITIAL_CLIENT_ID=my-university-app"])
@AutoConfigureMockMvc
class SecurityConfigProdTest(@Autowired val properties: ClientProperties) {
    @Test
    fun `should override default values with environment variable`() {
        assertEquals("my-university-app", properties.id)
    }
}
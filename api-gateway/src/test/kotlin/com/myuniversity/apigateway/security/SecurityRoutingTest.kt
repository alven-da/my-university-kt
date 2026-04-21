package com.myuniversity.apigateway.security

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono
import java.util.Base64

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8082) // Assume authentication-api is on this port
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SecurityRoutingTest (@Autowired val webClient: WebTestClient) {

    @Autowired
    private lateinit var rateLimiter: RateLimiter<Any> // Injects the Primary mock

    @TestConfiguration
    class TestConfig {
        @Bean
        @Primary
        fun mockRateLimiter(): RateLimiter<Any> {
            return mock(RateLimiter::class.java) as RateLimiter<Any>
        }
    }

    @BeforeEach
    fun setup() {
        // Default behavior: allow everything
        val allowedResponse = RateLimiter.Response(true, emptyMap())
        whenever(rateLimiter.isAllowed(anyString(), anyString()))
            .thenReturn(Mono.just(allowedResponse))
    }

    @Test
    fun `should return 429 when rate limit exceeded`() {
        // Given: Denied response - setting x-remaining to 0 to trigger rate-limited
        val deniedResponse = RateLimiter.Response(false, mapOf("X-Remaining" to "0"))

        whenever(rateLimiter.isAllowed(anyString(), anyString()))
            .thenReturn(Mono.just(deniedResponse))

        // 2. Perform the request
        webClient.post().uri("/auth/oauth2/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
            .exchange()
            // 3. This should now correctly return 429
            .expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
    }

    @Test
    fun `should pass forwarded headers to backend`() {
        stubFor(get(urlEqualTo("/.well-known/openid-configuration"))
            .willReturn(aResponse().withStatus(200)))

        webClient.get().uri("/auth/.well-known/openid-configuration")
            .header("X-Forwarded-Host", "localhost")
            .exchange()
            .expectStatus().isOk

        verify(getRequestedFor(urlEqualTo("/.well-known/openid-configuration"))
            .withHeader("X-Forwarded-Host", equalTo("localhost")))
    }

    @Test
    fun `should route token request to auth-api`() {
        stubFor(post(urlEqualTo("/oauth2/token"))
            .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("""{"access_token": "mock-token"}""")))

        webClient.post().uri("/auth/oauth2/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters.fromFormData("grant_type", "client_credentials")
                .with("scope", "openid"))
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("test-client:test-secret".toByteArray()))
            .exchange()
            .expectStatus().isOk
    }
}
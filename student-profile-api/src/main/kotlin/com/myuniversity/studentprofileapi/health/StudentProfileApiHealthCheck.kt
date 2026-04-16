package com.myuniversity.studentprofileapi.health

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class StudentProfileApiHealthCheck : HealthIndicator {
    override fun health(): Health? {
        val code = checkServiceStatus()

        return if (code != 0) {
            Health.down().withDetail("Error code: ", code).build()
        } else {
            Health.up().withDetail("Message", "student-profile-api service is running").build()
        }
    }

    private fun checkServiceStatus(): Int = 0
}
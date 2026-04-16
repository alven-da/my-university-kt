plugins {
    kotlin("jvm") version "2.3.10" apply false
    kotlin("plugin.spring") version "2.2.21" apply false

    id("org.springframework.boot") version "3.5.13" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

group = "com.myuniversity"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
    extra["springCloudVersion"] = "2025.0.0"
}

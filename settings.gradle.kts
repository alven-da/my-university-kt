plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "my-university"

include("api-gateway")
include("student-profile-api")
include("authentication-api")
include("shared-lib")
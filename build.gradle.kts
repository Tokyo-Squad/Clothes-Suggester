plugins {
    kotlin("jvm") version "2.1.10"
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation("io.insert-koin:koin-core:4.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}
val filteredCoverage = fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
    include("**/logic/usecase/**")
}
tasks.jacocoTestReport {
    dependsOn(tasks.test)
    classDirectories.setFrom(filteredCoverage)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}
tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom(filteredCoverage)
    violationRules {
        rule {
            limit {
                counter = "CLASS"
                value = "COVEREDRATIO"
                minimum = "0.8".toBigDecimal()
            }
        }
    }

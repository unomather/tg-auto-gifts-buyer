plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.10"
}

group = "com.nowiwr01p.middleware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    /** KTOR **/
    implementation("io.ktor:ktor-client-core:3.2.3")
    implementation("io.ktor:ktor-client-cio:3.2.3")
    implementation("io.ktor:ktor-client-logging:3.2.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.2.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")
    testImplementation("io.ktor:ktor-client-mock:3.2.3")
    /** KOIN **/
    implementation("io.insert-koin:koin-core:4.1.0")
    /** COROUTINES **/
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    /** LOGGERS **/
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    /** TEST **/
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
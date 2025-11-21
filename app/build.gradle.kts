plugins {
    alias(libs.plugins.jvm)
    application
    id("org.jetbrains.kotlinx.kover") version "0.6.1" // ← Plugin de coverage
}

repositories {
    mavenCentral()
}

dependencies {
    // ✅ SOLO dependencias esenciales SIN conflictos
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.mockk:mockk:1.13.8")
    
    // ✅ Kotlin STDlib para compilación
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "com.store.MainKt"
}
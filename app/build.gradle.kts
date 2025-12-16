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

    // 📝 Establece el nivel de log de las pruebas para ver los resultados detallados
    testLogging {
        events = mutableSetOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
        )
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true
    }

    // 🔄 Opcional: Deshabilitar el cache de Gradle para forzar re-ejecución (útil para debugging)
    outputs.upToDateWhen { false }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "com.store.MainKt"
}
plugins {
    alias(libs.plugins.jvm)
    application
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.mockk:mockk:1.13.8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
}

tasks.test {
    useJUnitPlatform()
    
    // 🔽 CONFIGURACIÓN PARA VER RESULTADOS
    testLogging {
        events("passed", "failed", "skipped")  // Mostrar qué pruebas pasan/fallan
        showStandardStreams = true             // Mostrar logs de los tests
        showExceptions = true                  // Mostrar excepciones si hay fallos
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "com.store.MainKt"
}
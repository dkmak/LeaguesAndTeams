plugins {
    kotlin("jvm")
    `java-library`
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Kotlin Serialization for Json
    implementation(libs.kotlinx.serialization.json)
}

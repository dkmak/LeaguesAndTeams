import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt.plugin) apply false
}

/* TODO: DEAL WITH LATER

plugins.withId(libs.plugins.android.application.get().pluginId){
    configure<com.android.build.api.dsl.ApplicationExtension> {
        setJvmToolchainAndCompileOptions(this)
    }
}

plugins.withId(libs.plugins.android.library.get().pluginId) {
    configure<com.android.build.api.dsl.LibraryExtension> {
        setJvmToolchainAndCompileOptions(this)
    }
}

// Helper function to avoid repetition.
private fun setJvmToolchainAndCompileOptions(commonExtension: com.android.build.api.dsl.CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        (this as ExtensionAware).extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }

            // Set the JVM toolchain that Gradle will use to run compilation.
            jvmToolchain(21)
        }
    }
}
*/

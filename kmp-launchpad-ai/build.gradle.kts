plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktLint)
    `maven-publish`
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        publishAllLibraryVariants()
    }
    jvmToolchain(17)
    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.bottlerocketstudios.launchpad.ai"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }
}

ktlint {
    ignoreFailures.set(true)
    android.set(true)

    filter {
        exclude("**/generated/**")
    }
}

group = extra["publishing.group"] as String
version = libs.versions.launchpad.ai.get()

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/BottleRocketStudios/KMP-LaunchPad-AI")
            credentials {
                username = System.getenv("REPO_READ_WRITE_USER") ?: System.getenv("GH_PUBLISH_USERNAME")
                password = System.getenv("REPO_READ_WRITE_TOKEN") ?: System.getenv("GH_PUBLISH_PASSWORD")
            }
        }
    }
}

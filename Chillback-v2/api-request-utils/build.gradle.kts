plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("native.cocoapods")
}

object Metadata {
    const val javaVersion = "11"
    val asJavaVersionEnum = JavaVersion.values().find { it.name.endsWith(Metadata.javaVersion) }
    const val minSDK = 21
    const val maxSDK = 33

    const val iosDeploymentTarget = "14.1"

    const val namespace = "com.deathsdoor.request.utilites"
    const val module = "api-request-utils"

}

object Ktor {
    private const val base = "io.ktor"
    private const val version = "2.0.0"
    const val android = "$base:ktor-client-okhttp:$version"
    const val ios = "$base:ktor-client-ios:$version"
    const val js = "$base:ktor-client-js:$version"
    const val jvm = "$base:ktor-client-cio:$version"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = Metadata.javaVersion
            }
        }
    }

    jvm("desktop"){
        compilations.all {
            kotlinOptions {
                jvmTarget = Metadata.javaVersion
            }
        }
    }

    js(IR){
        browser()
        nodejs()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Request-Utilities is a Kotlin Multiplatform library that provides a simple and efficient way to make HTTP requests in Kotlin Multiplatform projects. Request-Utilities is designed to be easy to use, customizable, and extendable."
        version = "1.0.1"
        ios.deploymentTarget = Metadata.iosDeploymentTarget
        framework {
            baseName = Metadata.module
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                api("io.ktor:ktor-client-core:2.2.4")
            }
        }

        val androidMain by getting {
            dependencies{
                implementation(Ktor.android)
            }
        }

        val desktopMain by getting {
            dependencies{
                implementation(Ktor.jvm)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies{
                implementation(Ktor.ios)
            }
        }

        val jsMain by getting {
            dependencies{
                implementation(Ktor.js)
            }
        }
    }
}


android {
    namespace = Metadata.namespace
    compileSdk = Metadata.maxSDK

    defaultConfig.minSdk = Metadata.minSDK
    defaultConfig.targetSdk = Metadata.maxSDK

    compileOptions.sourceCompatibility = Metadata.asJavaVersionEnum
    compileOptions.targetCompatibility = Metadata.asJavaVersionEnum
}
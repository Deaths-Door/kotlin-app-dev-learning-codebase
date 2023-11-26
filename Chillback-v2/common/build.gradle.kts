plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")

    id("com.android.library")
    id("org.jetbrains.compose")

    id("dev.icerock.mobile.multiplatform-resources")

    kotlin("plugin.serialization")

    id("kotlin-parcelize")
}

object Metadata {
    const val javaVersion = "11"
    val asJavaVersionEnum = JavaVersion.values().find { it.name.endsWith(javaVersion) }
    const val minSDK = 26
    const val maxSDK = 33

    const val iosDeploymentTarget = "14.1"

    const val namespace = "com.deathsdoor.chillback.common"

    const val version = "1.0.0"
}
object MusicFileMetadataExtractor {
    const val android = "net.jthink:jaudiotagger:3.0.0"
}

object GoogleLogin {
    const val android = "com.google.android.gms:play-services-auth:20.5.0"
}

object GoogleMaps {
    const val androidServices = "com.google.android.gms:play-services-maps:18.1.0"
    const val androidLocation = "com.google.android.gms:play-services-location:21.0.1"
    const val androidCompose = "com.google.maps.android:maps-compose:2.11.4"
}

multiplatformResources {
    multiplatformResourcesPackage = "${Metadata.namespace}.resources"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = Metadata.javaVersion
            }
        }
    }

    js(IR) {
        browser()
        nodejs()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = Metadata.version
        ios.deploymentTarget = Metadata.iosDeploymentTarget
    }



    sourceSets {
        val commonMain by getting {
            dependencies {
                //Basic Jetpack compose
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.runtime)

                //For Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

                //For Serialization / Deserialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                //For navigation
                implementation("io.github.xxfast:decompose-router:0.2.1")
                implementation("com.arkivanov.decompose:decompose:1.1.0")
                implementation("com.arkivanov.essenty:parcelable:1.1.0")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-compose-experimental-alpha-02")

                //For Viewmodel
                implementation("io.github.hoc081098:kmp-viewmodel:0.4.0")

                //For Resource handling (string localization)
                implementation("dev.icerock.moko:resources:0.22.0")
                implementation("dev.icerock.moko:resources-compose:0.22.0")

                //For Settings / LocalSongInfo
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

                //For Firebase
                listOf("auth","firestore","database").forEach {  implementation("dev.gitlive:firebase-$it:1.8.1") }

                //For UUID and URI
                listOf(":uuid",":uri").forEach { implementation(project(":$it")) }

                //For Media-player
                listOf("core","ui").forEach { implementation(project(":astroplayer-$it")) }

                //API-Request
                listOf("password-strength-checker","ticketmaster","musicmatch").forEach { implementation(project(":api-$it")) }

                //TODO check if removed or not
                //For timeppicker time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                //Basic
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                //TODO maybe keep this in the android build.gradle instead
                api("androidx.activity:activity-compose:1.7.2")

                //For Google Maps
                implementation(GoogleMaps.androidServices)
                implementation(GoogleMaps.androidLocation)
                implementation(GoogleMaps.androidCompose)

                //For Google Login
                implementation(GoogleLogin.android)

                //For Music Metadata reading
                implementation(MusicFileMetadataExtractor.android)

                //For Permissions + other features
                implementation("com.google.accompanist:accompanist-permissions:0.31.0-alpha")


                //TODO make a custom version of it for ios as well
                //For BottomSheet
                implementation("io.github.oleksandrbalan:modalsheet:0.5.0")

                //TODO make a custom version of it for all platforms as well
                //For Chiptextfeild
                implementation("io.github.dokar3:chiptextfield:0.4.7")

                //TODO use https://github.com/korlibs/korge/tree/main/korim instead for this
                //Image Loading
                implementation("io.coil-kt:coil-compose:2.3.0")
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

    buildFeatures.compose = true

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
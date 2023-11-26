import kotlin.math.pow

plugins {
    kotlin("android")
    id("com.android.application")
    id("org.jetbrains.compose")
}



object Metadata {
    const val javaVersion = "11"
    val asJavaVersionEnum = JavaVersion.values().find { it.name.endsWith(javaVersion) }
    const val minSDK = 26
    const val maxSDK = 33

    const val namespace = "com.deathsdoor.chillback"
    const val versionName = "1.0.0"
    val versionCode = versionName
        .split(".")
        .foldIndexed(0) { index, acc, part ->
            val versionPart = part.toIntOrNull() ?: 0
            if (versionPart == 0) acc
            else acc + versionPart * (1000000 / 100.0.pow(index)).toInt()
        }
}

android {
    namespace = Metadata.namespace
    compileSdk = Metadata.maxSDK

    defaultConfig.minSdk = Metadata.minSDK
    defaultConfig.targetSdk = Metadata.maxSDK

    compileOptions.sourceCompatibility = Metadata.asJavaVersionEnum
    compileOptions.targetCompatibility = Metadata.asJavaVersionEnum
    kotlinOptions.jvmTarget = Metadata.javaVersion

    defaultConfig.versionCode = Metadata.versionCode
    defaultConfig.versionName = Metadata.versionName
    defaultConfig.applicationId = Metadata.namespace

    buildTypes.getByName("release"){
        isMinifyEnabled = true
        isShrinkResources = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
}

dependencies {
    implementation(project(":common"))
}
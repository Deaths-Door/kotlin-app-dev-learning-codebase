plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false

    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false

    kotlin("plugin.serialization") apply false

    id("dev.icerock.mobile.multiplatform-resources") apply false
}


buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("dev.icerock.moko:resources-generator:0.22.0")
    }
}
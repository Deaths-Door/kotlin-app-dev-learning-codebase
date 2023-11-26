pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version(extra["kotlin.version"] as String)
        kotlin("android") version(extra["kotlin.version"] as String)
        id("com.android.application") version(extra["agp.version"] as String)
        id("com.android.library") version(extra["agp.version"] as String)
        id("org.jetbrains.compose") version(extra["compose.version"] as String)
        kotlin("plugin.serialization") version(extra["plugin.serialization.version"] as String)

        id("dev.icerock.mobile.multiplatform-resources") version(extra["moko.resource.version"] as String)
    }
}


dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}


//Shared module
include(":common")

//Platfroms
include(":android",":web","ios",)

//Mediaplayer
include(":astroplayer-core", ":astroplayer-ui")

//APIs
include(":api-request-utils",":api-ticketmaster",":api-musicmatch",":api-password-strength-checker")

//Common classes
include(":uuid")
include(":uri")

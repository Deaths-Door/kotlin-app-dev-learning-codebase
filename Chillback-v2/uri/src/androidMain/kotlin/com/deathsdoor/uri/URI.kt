package com.deathsdoor.uri

import kotlinx.serialization.Serializable
import java.io.File
import android.net.Uri as PlatformURI
@Serializable
@JvmInline
actual value class URI internal actual constructor(internal actual val uri: String) {
    constructor(uri: PlatformURI) : this(uri.toString())

    actual override fun toString(): String = uri
    actual companion object {
        actual fun fromFile(filePath : String): URI = fromFile(File(filePath))

        fun fromFile(file: File): URI = URI(PlatformURI.fromFile(file))

        actual fun fromURL(url : String) : URI = URI(PlatformURI.parse(url))

        actual fun parse(uriString : String) : URI {
            val file = File(uriString)
            return if(file.exists()) fromFile(file)
            else fromURL(uriString)
        }
    }
}
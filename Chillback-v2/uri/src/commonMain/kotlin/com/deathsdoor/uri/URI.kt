package com.deathsdoor.uri

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
expect value class URI internal actual constructor(internal val uri : String) {
    override fun toString(): String
    companion object {
        fun fromFile(filePath : String): URI

        fun fromURL(url : String) : URI

        fun parse(uriString : String) : URI
    }
}
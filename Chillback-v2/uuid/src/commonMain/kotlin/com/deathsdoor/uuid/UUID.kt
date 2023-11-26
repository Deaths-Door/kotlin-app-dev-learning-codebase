package com.deathsdoor.uuid

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
expect value class UUID internal actual constructor(internal val uuid: String) {
    override fun toString(): String
    companion object {
        fun fromString(string : String) : UUID
        fun random() : UUID
    }
}
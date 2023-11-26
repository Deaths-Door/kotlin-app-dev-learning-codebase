package com.deathsdoor.uuid
import kotlinx.serialization.Serializable
import java.util.UUID as PlatformUUID

@Serializable
@JvmInline
actual value class UUID internal actual constructor(internal actual val uuid: String) {
    actual companion object {
        actual fun fromString(string: String): UUID = UUID(PlatformUUID.fromString(string).toString())
        actual fun random(): UUID = UUID(PlatformUUID.randomUUID().toString())
    }
}
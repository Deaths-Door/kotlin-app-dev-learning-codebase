package com.deathsdoor.request.utilities.extensions

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer


inline fun <reified T> Encoder.encodeValue(value : T) = this.encodeSerializableValue(JsonObject.serializer(),Json.encodeToJsonElement(value).jsonObject)
inline fun <reified T> Encoder.encodeValue(serializer: SerializationStrategy<T>, value : T) = this.encodeSerializableValue(serializer,value)
inline fun <reified T> T.encodeAsJsonElement(): JsonElement = Json.encodeToJsonElement(serializer(),this)

inline fun <reified T> List<T>.encodeAsJsonElement(): List<JsonElement> = this.map { it.encodeAsJsonElement() }
inline fun <reified T> List<T>.encodeAsJsonElement(action:(T) -> JsonElement): List<JsonElement> = this.map { action(it) }

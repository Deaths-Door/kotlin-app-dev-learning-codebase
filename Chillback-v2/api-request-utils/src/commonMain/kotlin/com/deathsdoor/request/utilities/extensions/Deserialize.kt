package com.deathsdoor.request.utilities.extensions

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject


inline fun<reified T> deserializeFromJsonElement(value : JsonElement) = Json.decodeFromJsonElement<T>(value)
inline fun<reified T> deserializeFromString(value : String) = Json.decodeFromString<T>(value)

inline fun<reified T> JsonElement.deserialize() = Json.decodeFromJsonElement<T>(this)
inline fun<reified T> JsonObject.deserialize() = Json.decodeFromJsonElement<T>(this)
inline fun<reified T> JsonPrimitive.deserialize() = Json.decodeFromJsonElement<T>(this)

inline fun<T : Any> JsonElement.deserialize(deserializer: DeserializationStrategy<T>) = Json.decodeFromJsonElement<T>(deserializer,this)
inline fun<reified T> String.deserialize() = Json.decodeFromString<T>(this)

inline fun <reified T> JsonElement.deserializeList(arrayName : String? = null) : List<T> = when {
    this.isJsonObject -> this.jsonObject[arrayName]!!.jsonArray.mapNotNull { it.deserialize() }
    else -> this.jsonArray.mapNotNull { it.deserialize() }
}
package com.deathsdoor.request.utilities.extensions

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

val Decoder.asJsonElement: JsonElement get() = decodeSerializableValue(JsonElement.serializer())
val Decoder.asJsonObject: JsonObject get() = asJsonElement.jsonObject
val Decoder.asJsonArray: JsonArray get() = asJsonElement.jsonArray
val Decoder.asJsonPrimitive: JsonPrimitive get() = asJsonElement.jsonPrimitive
val Decoder.asJsonNull: JsonNull get() = asJsonElement.jsonNull

val JsonElement.isJsonObject: Boolean get() = this is JsonObject
val JsonElement.isJsonArray: Boolean get() = this is JsonArray
val JsonElement.isJsonPrimitive: Boolean get() = this is JsonPrimitive
val JsonElement.isJsonNull: Boolean get() = this is JsonNull
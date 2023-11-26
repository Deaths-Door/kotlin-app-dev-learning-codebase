package com.deathsdoor.musicmatch.dataclasses

import com.deathsdoor.request.utilities.protected.hasToBeInRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

//TODO add serialized name to handle it
@Serializable
data class MusixMood(@SerialName("label") val name: String, @SerialName("value") val value: Float) {
    init {
        value.hasToBeInRange(0f,1f,"value")
    }

    enum class RawData{
        Valence,
        Arousal
    }

    //TODO remove this
    internal companion object {
        val extractRawData : (JsonObject?) -> Map<RawData, Double> = {
            mapOf(
                RawData.Valence to it!!["raw_data"]!!.jsonObject["valence"]!!.jsonPrimitive.double,
                RawData.Arousal to it["raw_data"]!!.jsonObject["arousal"]!!.jsonPrimitive.double
            )
        }
    }
}
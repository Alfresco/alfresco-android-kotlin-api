package com.alfresco.auth.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonException

@Serializable
data class ContentServerDetailsData(
    val edition: String,
    val version: String,
    val schema: String
)

@Serializable
data class ContentServerDetails(
    val data: ContentServerDetailsData
) {
    fun isAtLeast(minVersion: String): Boolean {
        val minParts = minVersion.split(".")
        val verParts = data.version.split(".")
        for ((index, value) in minParts.withIndex()) {
            val part = if (index > verParts.size - 1) 0 else verParts[index].toInt()
            val min = value.toInt()
            if (part > min) {
                return true
            } else if (part < min) {
                return false
            }
        }
        return true
    }

    companion object {
        fun jsonDeserialize(str: String): ContentServerDetails? {
            return try {
                Json(JsonConfiguration.Stable).parse(serializer(), str)
            } catch (ex: JsonException) {
                null
            } catch (ex: SerializationException) {
                null
            }
        }
    }
}

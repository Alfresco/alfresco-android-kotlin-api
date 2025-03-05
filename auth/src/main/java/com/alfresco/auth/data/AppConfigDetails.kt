package com.alfresco.auth.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@OptIn(InternalSerializationApi::class)
@Serializable
data class MobileSettings(
    val https: Boolean,
    val port: Int,
    val realm: String?,
    var host: String,
    val secret: String?,
    val scope: String,
    val contentServicePath: String?,
    val audience: String?,
    val android: AndroidSettings,
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AndroidSettings(
    val redirectUri: String,
    val clientId: String
)

@OptIn(InternalSerializationApi::class)
@Serializable
data class AppConfigDetails(
    var mobileSettings: MobileSettings?
) {
    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun jsonDeserialize(str: String): AppConfigDetails? {
            return try {
                json.decodeFromString<AppConfigDetails>(serializer(), str)
            } catch (ex: SerializationException) {
                ex.printStackTrace()
                null
            }
        }
    }
}

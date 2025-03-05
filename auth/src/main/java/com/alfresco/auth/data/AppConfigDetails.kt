package com.alfresco.auth.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@OptIn(InternalSerializationApi::class)
@Serializable
data class MobileSettings(
    var https: Boolean? = false,
    var port: Int? = null,
    var realm: String?,
    var host: String,
    var secret: String?,
    var scope: String,
    var contentServicePath: String?,
    var audience: String?,
    var android: AndroidSettings,
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

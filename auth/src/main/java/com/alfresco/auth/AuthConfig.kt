package com.alfresco.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonException

@Serializable
data class AuthConfig(
    /**
     * Defines if the connection should be https or not
     */
    var https: Boolean,

    /**
     * The id of the client
     */
    var clientId: String,

    /**
     * The realm. Used to generate the final url.
     * The place of realm should be here https://identity-service/auth/realms/REALM/
     */
    var realm: String,

    /**
     * The redirect url
     */
    var redirectUrl: String,

    /**
     * Port for the network connection
     */
    var port: String,

    /**
     * Url path for service documents
     */
    var serviceDocuments: String
) {
    fun jsonSerialize(): String {
        return Json(JsonConfiguration.Stable).stringify(serializer(), this)
    }

    companion object {
        fun jsonDeserialize(str: String): AuthConfig? {
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

package com.alfresco.auth.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Serializable
data class OAuth2Data(
    val host: String,
    val clientId: String,
    val secret: String,
    val scope: String,
    val implicitFlow: Boolean,
    val codeFlow: Boolean,
    val silentLogin: Boolean,
    val publicUrls: List<String>,
    val redirectSilentIframeUri: String,
    val redirectUri: String,
    val logoutUrl: String,
    val logoutParameters: List<String>,
    val redirectUriLogout: String,
    val audience: String,
    val skipIssuerCheck: Boolean,
    val strictDiscoveryDocumentValidation: Boolean
)

@Serializable
internal data class AppConfigDetails(
    val oauth2: OAuth2Data
) {
    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun jsonDeserialize(str: String): AppConfigDetails? {
            return try {
                json.decodeFromString(serializer(), str)
            } catch (ex: SerializationException) {
                null
            }
        }
    }
}

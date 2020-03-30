package com.alfresco.auth

import com.google.gson.Gson
import com.google.gson.JsonParseException

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
        return Gson().toJson(this)
    }

    companion object {
        fun jsonDeserialize(str: String): AuthConfig? {
            return try {
                Gson().fromJson(str, AuthConfig::class.java)
            } catch (ex: JsonParseException) {
                null
            }
        }
    }
}

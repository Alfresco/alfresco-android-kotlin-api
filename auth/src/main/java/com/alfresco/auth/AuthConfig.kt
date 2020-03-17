package com.alfresco.auth

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
)

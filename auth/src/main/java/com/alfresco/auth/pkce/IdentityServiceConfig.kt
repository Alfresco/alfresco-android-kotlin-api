package com.alfresco.auth.pkce

/**
 * Created by Bogdan Roatis on 10/7/2019.
 */
data class IdentityServiceConfig(

    /**
     * The realm. Used to generate the final url.
     * The place of realm should be here https://identity-service/auth/realms/REALM/
     */
    var realm: String,

    /**
     * The issuer url, e.g. "https://accounts.google.com"
     */
    var issuerUrl: String
)

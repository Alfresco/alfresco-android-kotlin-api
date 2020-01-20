package com.alfresco.auth.basic.config

/**
 * Created by Bogdan Roatis on 10/2/2019.
 */
data class BasicAuthConfig(
    override var baseUrl: String,
    override var clientId: String,
    override var realm: String,
    var clientSecret: String
) : AuthConfig(baseUrl = baseUrl, clientId = clientId, realm = realm)

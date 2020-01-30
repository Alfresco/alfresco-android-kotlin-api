package com.alfresco.auth.pkce

/**
 * Created by Bogdan Roatis on 10/4/2019.
 */
data class DiscoveryData(
    var issuer: String,
    var authorizationEndpoint: String,
    var tokenEndpoint: String,
    var tokenIntrospectionEndpoint: String,
    var userInfoEndpoint: String,
    var endSessionEndpoint: String,
    var jwksUri: String,
    var checkSessionIframe: String
)

package com.alfresco.auth

/**
 * Created by Bogdan Roatis on 7/31/2019.
 */
enum class AuthType {

    /**
     * Used to specify the need of basic auth with username and password
     */
    BASIC,

    /**
     * Used to specify the need of SAML auth
     */
    SAML,

    /**
     * Used to specify the need of SSO auth
     */
    SSO,

    /**
     * Used to specify that the auth type is unknown
     */
    UNKNOWN
}

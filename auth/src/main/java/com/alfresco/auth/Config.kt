package com.alfresco.auth

import android.net.Uri

/**
 * Created by Bogdan Roatis on 8/27/2019.
 */
@Deprecated("This is going to be removed in the future")
object Config {
    const val DEFAULT_DOMAIN = "http://alfresco-identity-service.mobile.dev.alfresco.me"
    const val REALM = "alfresco"
    const val BASE_URL = "$DEFAULT_DOMAIN/auth/realms/$REALM/protocol/openid-connect"
    const val CLIENT_ID = "iosapp"
    const val REDIRECT_URI_PKCE = "iosapp://fake.url.here/auth"
    const val DISCOVERY_URL = "http://alfresco-identity-service.mobile.dev.alfresco.me/auth/realms/alfresco/"
    const val REDIRECT_URI = "*"
    const val SCOPE = "openid"
    const val RESPONSE_TYPE = "id_token%20token&nonce"
    const val SAML_AUTH_PATH = "/auth"
    const val BASIC_AUTH_PATH = "/token"
    const val GRANT_TYPE = "password"

    fun getDefaultBasicAuthUri() =
            Uri.parse(BASE_URL + BASIC_AUTH_PATH).buildUpon()
//                .appendQueryParameter("grant_type", GRANT_TYPE)
                    .appendQueryParameter("client_id", CLIENT_ID)
                    .build()

    fun getDefaultSamlAuthUri() =
            Uri.parse(BASE_URL + SAML_AUTH_PATH).buildUpon()
                    .appendQueryParameter("client_id", CLIENT_ID)
                    .appendQueryParameter("redirect_uri", REDIRECT_URI)
                    .appendQueryParameter("scope", SCOPE)
                    .appendQueryParameter("response_type", RESPONSE_TYPE)
                    .build()
}

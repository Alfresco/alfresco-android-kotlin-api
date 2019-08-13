package com.alfresco.auth.saml.data

import android.net.Uri


/**
 * Created by Bogdan Roatis on 7/25/2019.
 */

private const val ID_TOKEN: String = "id_token"
private const val SESSION_STATE: String = "session_state"
private const val ACCESS_TOKEN: String = "access_token"
private const val TOKEN_TYPE: String = "token_type"
private const val EXPIRES_IN: String = "expires_in"

const val SAML_LOGIN_GATEWAY_URL = "https://gateway.aps2dev.envalfresco.com/admin/"
const val BASIC_LOGIN_URL = "http://alfresco-identity-service.mobile.dev.alfresco.me/auth/realms/alfresco/protocol/openid-connect/token"

fun getSamlResponse(url: String?): SamlCredentials? {
    if (url == null) {
        return null
    }

    val uri = Uri.parse(url.replace("#", "?"))

    return getSamlResponse(uri)
}

fun getSamlResponse(uri: Uri?): SamlCredentials? {
    if (uri == null) {
        return null
    }

    val builtUrl = "${uri.scheme}://${uri.authority}${uri.path}"
    if (SAML_LOGIN_GATEWAY_URL != builtUrl ||
            !uri.queryParameterNames.contains(ACCESS_TOKEN)) {
        return null
    }

    val idToken = uri.getQueryParameter(ID_TOKEN)
    val sessionState = uri.getQueryParameter(SESSION_STATE)
    val accessToken = uri.getQueryParameter(ACCESS_TOKEN)
    val tokenType = uri.getQueryParameter(TOKEN_TYPE)
    val expiresIn = uri.getQueryParameter(EXPIRES_IN)

    return SamlCredentials(
            id_token = idToken,
            session_state = sessionState,
            access_token = accessToken,
            token_type = tokenType,
            expires_in = expiresIn)
}

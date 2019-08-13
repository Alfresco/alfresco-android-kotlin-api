package com.alfresco.auth.saml.data

import java.io.Serializable

/**
 * Created by Bogdan Roatis on 7/25/2019.
 */
data class SamlCredentials(
        val id_token: String?,
        val session_state: String?,
        val access_token: String?,
        val token_type: String?,
        val expires_in: String?
) : Serializable

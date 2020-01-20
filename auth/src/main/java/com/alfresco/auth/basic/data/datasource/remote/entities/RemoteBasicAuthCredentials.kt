package com.alfresco.auth.basic.data.datasource.remote.entities

import com.alfresco.core.data.remote.BasicResponse
import java.io.Serializable

/**
 * Created by Bogdan Roatis on 8/6/2019.
 */
data class RemoteBasicAuthCredentials(
    val scope: String?,
    val token_type: String?,
    val `not-before-policy`: Int?,
    val session_state: String?,
    val refresh_token: String?,
    val refresh_expires_in: Int?,
    val expires_in: Int?,
    val access_token: String?
) : BasicResponse(), Serializable

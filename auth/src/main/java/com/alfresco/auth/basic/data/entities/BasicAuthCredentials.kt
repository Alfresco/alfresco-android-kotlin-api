package com.alfresco.auth.basic.data.entities

import com.alfresco.core.data.remote.BasicResponse
import java.io.Serializable

/**
 * Created by Bogdan Roatis on 8/28/2019.
 */
data class BasicAuthCredentials(
    val scope: String?,
    val tokenType: String?,
    val notBeforePolicy: Int?,
    val sessionState: String?,
    val refreshToken: String?,
    val refreshExpiresIn: Int?,
    val expiresIn: Int?,
    val accessToken: String?
) : BasicResponse(), Serializable

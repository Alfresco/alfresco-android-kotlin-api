package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Marked as AuthConfiguration
 */
@JsonClass(generateAdapter = true)
data class AuthConfiguration(
    @Json(name = "authUrl") @field:Json(name = "authUrl") var authUrl: String? = null,
    @Json(name = "realm") @field:Json(name = "realm") var realm: String? = null,
    @Json(name = "clientId") @field:Json(name = "clientId") var clientId: String? = null,
    @Json(name = "useBrowserLogout") @field:Json(name = "useBrowserLogout") var useBrowserLogout: Boolean? = null
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Marked as SystemProperties
 */
@JsonClass(generateAdapter = true)
data class SystemProperties(
    @Json(name = "allowInvolveByEmail") @field:Json(name = "allowInvolveByEmail") var allowInvolveByEmail: Boolean? = null,
    @Json(name = "disableJavaScriptEventsInFormEditor") @field:Json(name = "disableJavaScriptEventsInFormEditor") var disableJavaScriptEventsInFormEditor: Boolean? = null,
    @Json(name = "logoutDisabled") @field:Json(name = "logoutDisabled") var logoutDisabled: Boolean? = null,
    @Json(name = "alfrescoContentSsoEnabled") @field:Json(name = "alfrescoContentSsoEnabled") var alfrescoContentSsoEnabled: Boolean? = null,
    @Json(name = "authConfiguration") @field:Json(name = "authConfiguration") var authConfiguration: AuthConfiguration? = null
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Marked as RequestLinkContent
 */
@JsonClass(generateAdapter = true)
data class RequestLinkContent(
    @Json(name = "source") @field:Json(name = "source") var source: String? = null,
    @Json(name = "mimeType") @field:Json(name = "mimeType") var mimeType: String? = null,
    @Json(name = "sourceId") @field:Json(name = "sourceId") var sourceId: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null
)

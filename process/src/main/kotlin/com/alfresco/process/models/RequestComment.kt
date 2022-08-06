package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property message
 */
@JsonClass(generateAdapter = true)
data class RequestComment(
    @Json(name = "message") @field:Json(name = "message") var message: String? = null
)

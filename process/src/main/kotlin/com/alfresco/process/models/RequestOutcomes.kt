package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property values
 * @property outcome
 */
@JsonClass(generateAdapter = true)
data class RequestOutcomes(
    @Json(name = "values") @field:Json(name = "values") var values: Map<String, Any?>? = null,
    @Json(name = "outcome") @field:Json(name = "outcome") var outcome: String? = null
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
/**
 * Marked as RequestProcessInstances
 */
@JsonClass(generateAdapter = true)
data class RequestProcessInstancesQuery(
    @Json(name = "sort") @field:Json(name = "sort") var sort: String? = null,
    @Json(name = "state") @field:Json(name = "state") var state: String? = null
)

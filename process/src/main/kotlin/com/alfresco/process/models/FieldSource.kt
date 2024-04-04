package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property serviceId
 * @property name
 */
@JsonClass(generateAdapter = true)
data class FieldSource(
    @Json(name = "serviceId") @field:Json(name = "serviceId") var serviceId: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
)

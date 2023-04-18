package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property name
 * @property type
 * @property value
 */
@JsonClass(generateAdapter = true)
data class VariablesDataEntry(
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "value") @field:Json(name = "value") var value: Any? = null
)

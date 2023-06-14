package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property type
 * @property value
 */
@JsonClass(generateAdapter = true)
data class ResultFormVariables(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "value") @field:Json(name = "value") var value: Any? = null

)

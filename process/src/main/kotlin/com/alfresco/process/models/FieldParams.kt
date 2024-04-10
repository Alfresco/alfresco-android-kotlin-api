package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property fractionLength
 * @property multiple
 * @property fileSource
 * @property field
 * @property dateDisplayFormat
 */
@JsonClass(generateAdapter = true)
data class FieldParams(
    @Json(name = "fractionLength") @field:Json(name = "fractionLength") var fractionLength: Int = 0,
    @Json(name = "multiple") @field:Json(name = "multiple") var multiple: Boolean? = null,
    @Json(name = "fileSource") @field:Json(name = "fileSource") var fileSource: FieldSource? = null,
    @Json(name = "field") @field:Json(name = "field") var field: Fields? = null,
    @Json(name = "dateDisplayFormat") @field:Json(name = "dateDisplayFormat") var dateDisplayFormat: FieldSource? = null,
)

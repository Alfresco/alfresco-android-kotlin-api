package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property fractionLength
 * @property multiple
 * @property fileSource
 */
@JsonClass(generateAdapter = true)
data class FieldParams(
    @Json(name = "fractionLength") @field:Json(name = "fractionLength") var fractionLength: Int = 0,
    @Json(name = "multiple") @field:Json(name = "multiple") var multiple: Boolean? = null,
    @Json(name = "fileSource") @field:Json(name = "fileSource") var fileSource: FieldSource? = null,
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property fractionLength
 */
@JsonClass(generateAdapter = true)
data class FieldParams(
    @Json(name = "fractionLength") @field:Json(name = "fractionLength") var fractionLength: Int = 0,
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property values
 */
@JsonClass(generateAdapter = true)
data class RequestSaveForm(
    @Json(name = "values") @field:Json(name = "values") var values: ValuesModel? = null
)

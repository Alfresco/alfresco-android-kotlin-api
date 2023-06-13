package com.alfresco.process.models

import com.squareup.moshi.Json

/**
 * @property values
 */
data class RequestSaveForm(
    @Json(name = "values") @field:Json(name = "values") var values: ValuesModel? = null
)

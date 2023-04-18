package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property name
 * @property processDefinitionId
 * @property values
 */
@JsonClass(generateAdapter = true)
data class RequestCreateProcessInstance(
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "processDefinitionId") @field:Json(name = "processDefinitionId") var processDefinitionId: String? = null,
    @Json(name = "values") @field:Json(name = "values") var values: ValuesModel? = null
)

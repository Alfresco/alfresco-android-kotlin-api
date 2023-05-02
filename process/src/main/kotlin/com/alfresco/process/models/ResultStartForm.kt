package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultStartForm(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "processDefinitionId") @field:Json(name = "processDefinitionId") var processDefinitionId: String? = null,
    @Json(name = "processDefinitionName") @field:Json(name = "processDefinitionName") var processDefinitionName: String? = null,
    @Json(name = "processDefinitionKey") @field:Json(name = "processDefinitionKey") var processDefinitionKey: String? = null,
    @Json(name = "fields") @field:Json(name = "fields") var fields: List<Fields>? = null
)

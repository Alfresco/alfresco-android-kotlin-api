package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property name
 * @property processDefinitionId
 * @property processDefinitionName
 * @property processDefinitionKey
 * @property taskId
 * @property taskName
 * @property taskDefinitionKey
 * @property fields
 */
@JsonClass(generateAdapter = true)
data class ResultForm(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "processDefinitionId") @field:Json(name = "processDefinitionId") var processDefinitionId: String? = null,
    @Json(name = "processDefinitionName") @field:Json(name = "processDefinitionName") var processDefinitionName: String? = null,
    @Json(name = "processDefinitionKey") @field:Json(name = "processDefinitionKey") var processDefinitionKey: String? = null,
    @Json(name = "taskId") @field:Json(name = "taskId") var taskId: String? = null,
    @Json(name = "taskName") @field:Json(name = "taskName") var taskName: String? = null,
    @Json(name = "taskDefinitionKey") @field:Json(name = "taskDefinitionKey") var taskDefinitionKey: String? = null,
    @Json(name = "fields") @field:Json(name = "fields") var fields: List<Fields>? = null,
    @Json(name = "outcomes") @field:Json(name = "outcomes") var outcomes: List<CommonOptionModel>? = null

)

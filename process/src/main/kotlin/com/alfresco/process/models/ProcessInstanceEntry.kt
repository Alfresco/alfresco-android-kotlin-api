package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property name
 * @property businessKey
 * @property processDefinitionId
 * @property tenantId
 * @property started
 * @property ended
 * @property startedBy
 * @property processDefinitionName
 * @property processDefinitionDescription
 * @property processDefinitionKey
 * @property processDefinitionCategory
 * @property processDefinitionVersion
 * @property processDefinitionDeploymentId
 * @property graphicalNotationDefined
 * @property startFormDefined
 * @property suspended
 */
@JsonClass(generateAdapter = true)
data class ProcessInstanceEntry(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "businessKey") @field:Json(name = "businessKey") var businessKey: String? = null,
    @Json(name = "processDefinitionId") @field:Json(name = "processDefinitionId") var processDefinitionId: String? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: String? = null,
    @Json(name = "started") @field:Json(name = "started") var started: ZonedDateTime? = null,
    @Json(name = "ended") @field:Json(name = "ended") var ended: ZonedDateTime? = null,
    @Json(name = "startedBy") @field:Json(name = "startedBy") var startedBy: UserInfo? = null,
    @Json(name = "processDefinitionName") @field:Json(name = "processDefinitionName") var processDefinitionName: String? = null,
    @Json(name = "processDefinitionDescription") @field:Json(name = "processDefinitionDescription") var processDefinitionDescription: String? = null,
    @Json(name = "processDefinitionKey") @field:Json(name = "processDefinitionKey") var processDefinitionKey: String? = null,
    @Json(name = "processDefinitionCategory") @field:Json(name = "processDefinitionCategory") var processDefinitionCategory: String? = null,
    @Json(name = "processDefinitionVersion") @field:Json(name = "processDefinitionVersion") var processDefinitionVersion: Int? = null,
    @Json(name = "processDefinitionDeploymentId") @field:Json(name = "processDefinitionDeploymentId") var processDefinitionDeploymentId: Int? = null,
    @Json(name = "graphicalNotationDefined") @field:Json(name = "graphicalNotationDefined") var graphicalNotationDefined: Boolean? = null,
    @Json(name = "startFormDefined") @field:Json(name = "startFormDefined") var startFormDefined: Boolean? = null,
    @Json(name = "suspended") @field:Json(name = "suspended") var suspended: Boolean? = null,
    @Json(name = "variables") @field:Json(name = "variables") var data: List<VariablesDataEntry>? = null
)

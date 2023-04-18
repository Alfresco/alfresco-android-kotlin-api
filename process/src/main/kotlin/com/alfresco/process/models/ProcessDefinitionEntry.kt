package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property name
 * @property description
 * @property key
 * @property category
 * @property version
 * @property deploymentId
 * @property tenantId
 * @property hasStartForm
 */
@JsonClass(generateAdapter = true)
data class ProcessDefinitionEntry(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "key") @field:Json(name = "key") var key: String? = null,
    @Json(name = "category") @field:Json(name = "category") var category: String? = null,
    @Json(name = "version") @field:Json(name = "version") var version: Int? = null,
    @Json(name = "deploymentId") @field:Json(name = "deploymentId") var deploymentId: String? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: String? = null,
    @Json(name = "hasStartForm") @field:Json(name = "hasStartForm") var hasStartForm: Boolean? = null
)

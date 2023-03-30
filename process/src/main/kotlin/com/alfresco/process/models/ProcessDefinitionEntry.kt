package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property defaultAppId
 * @property name
 * @property description
 * @property modelId
 * @property theme
 * @property icon
 * @property deploymentId
 * @property key
 * @property category
 * @property version
 * @property tenantId
 * @property hasStartForm
 */
@JsonClass(generateAdapter = true)
data class ProcessDefinitionEntry(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "defaultAppId") @field:Json(name = "defaultAppId") var defaultAppId: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "modelId") @field:Json(name = "modelId") var modelId: Int? = null,
    @Json(name = "theme") @field:Json(name = "theme") var theme: String? = null,
    @Json(name = "icon") @field:Json(name = "icon") var icon: String? = null,
    @Json(name = "deploymentId") @field:Json(name = "deploymentId") var deploymentId: String? = null,
    @Json(name = "key") @field:Json(name = "key") var key: String? = null,
    @Json(name = "category") @field:Json(name = "category") var category: String? = null,
    @Json(name = "version") @field:Json(name = "version") var version: Int? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: Int? = null,
    @Json(name = "hasStartForm") @field:Json(name = "hasStartForm") var hasStartForm: Boolean? = null
)

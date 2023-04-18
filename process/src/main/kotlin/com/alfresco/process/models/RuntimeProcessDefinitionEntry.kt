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
 * @property tenantId
 */
@JsonClass(generateAdapter = true)
data class RuntimeProcessDefinitionEntry(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "defaultAppId") @field:Json(name = "defaultAppId") var defaultAppId: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "modelId") @field:Json(name = "modelId") var modelId: Int? = null,
    @Json(name = "theme") @field:Json(name = "theme") var theme: String? = null,
    @Json(name = "icon") @field:Json(name = "icon") var icon: String? = null,
    @Json(name = "deploymentId") @field:Json(name = "deploymentId") var deploymentId: String? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: Int? = null
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property name
 * @property externalId
 * @property status
 * @property parentGroupId
 * @property tenantId
 * @property type
 * @property lastSyncTimeStamp
 * @property userCount
 * @property users
 * @property capabilities
 * @property groups
 * @property manager
 */
@JsonClass(generateAdapter = true)
data class GroupInfo(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "externalId") @field:Json(name = "externalId") var externalId: Int? = null,
    @Json(name = "status") @field:Json(name = "status") var status: String? = null,
    @Json(name = "parentGroupId") @field:Json(name = "parentGroupId") var parentGroupId: Int? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: Int? = null,
    @Json(name = "type") @field:Json(name = "type") var type: Int? = null,
    @Json(name = "lastSyncTimeStamp") @field:Json(name = "lastSyncTimeStamp") var lastSyncTimeStamp: String? = null,
    @Json(name = "userCount") @field:Json(name = "userCount") var userCount: Int? = null,
    @Json(name = "users") @field:Json(name = "users") var users: String? = null,
    @Json(name = "capabilities") @field:Json(name = "capabilities") var capabilities: String? = null,
    @Json(name = "groups") @field:Json(name = "groups") var groups: String? = null,
    @Json(name = "manager") @field:Json(name = "manager") var manager: String? = null
)

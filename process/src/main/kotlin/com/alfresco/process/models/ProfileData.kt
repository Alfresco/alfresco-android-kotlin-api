package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property firstName
 * @property lastName
 * @property email
 * @property externalId
 * @property company
 * @property pictureId
 * @property fullname
 * @property password
 * @property type
 * @property status
 * @property created
 * @property lastUpdate
 * @property tenantId
 * @property latestSyncTimeStamp
 * @property groups
 * @property capabilities
 * @property apps
 * @property primaryGroup
 * @property tenantPictureId
 * @property tenantName
 */
@JsonClass(generateAdapter = true)
data class ProfileData(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "firstName") @field:Json(name = "firstName") var firstName: String? = null,
    @Json(name = "lastName") @field:Json(name = "lastName") var lastName: String? = null,
    @Json(name = "email") @field:Json(name = "email") var email: String? = null,
    @Json(name = "externalId") @field:Json(name = "externalId") var externalId: Int? = null,
    @Json(name = "company") @field:Json(name = "company") var company: String? = null,
    @Json(name = "pictureId") @field:Json(name = "pictureId") var pictureId: Int? = null,
    @Json(name = "fullname") @field:Json(name = "fullname") var fullname: String? = null,
    @Json(name = "password") @field:Json(name = "password") var password: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "status") @field:Json(name = "status") var status: String? = null,
    @Json(name = "created") @field:Json(name = "created") var created: ZonedDateTime? = null,
    @Json(name = "lastUpdate") @field:Json(name = "lastUpdate") var lastUpdate: ZonedDateTime? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: Int? = null,
    @Json(name = "latestSyncTimeStamp") @field:Json(name = "latestSyncTimeStamp") var latestSyncTimeStamp: String? = null,
    @Json(name = "groups") @field:Json(name = "groups") var groups: List<GroupInfo>? = null,
    @Json(name = "capabilities") @field:Json(name = "capabilities") var capabilities: String? = null,
    @Json(name = "apps") @field:Json(name = "apps") var apps: List<String>? = null,
    @Json(name = "primaryGroup") @field:Json(name = "primaryGroup") var primaryGroup: String? = null,
    @Json(name = "tenantPictureId") @field:Json(name = "tenantPictureId") var tenantPictureId: Int? = null,
    @Json(name = "tenantName") @field:Json(name = "tenantName") var tenantName: String? = null
)

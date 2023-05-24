package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property tenantId
 * @property created
 * @property lastUpdated
 * @property alfrescoTenantId
 * @property repositoryUrl
 * @property shareUrl
 * @property name
 * @property secret
 * @property authenticationType
 * @property version
 * @property sitesFolder
 */
@JsonClass(generateAdapter = true)
data class AccountInfo(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "tenantId") @field:Json(name = "tenantId") var tenantId: Int? = null,
    @Json(name = "created") @field:Json(name = "created") var created: ZonedDateTime? = null,
    @Json(name = "lastUpdated") @field:Json(name = "lastUpdated") var lastUpdated: ZonedDateTime? = null,
    @Json(name = "alfrescoTenantId") @field:Json(name = "alfrescoTenantId") var alfrescoTenantId: String? = null,
    @Json(name = "repositoryUrl") @field:Json(name = "repositoryUrl") var repositoryUrl: String? = null,
    @Json(name = "shareUrl") @field:Json(name = "shareUrl") var shareUrl: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "secret") @field:Json(name = "secret") var secret: String? = null,
    @Json(name = "authenticationType") @field:Json(name = "authenticationType") var authenticationType: String? = null,
    @Json(name = "version") @field:Json(name = "version") var version: String? = null,
    @Json(name = "sitesFolder") @field:Json(name = "sitesFolder") var sitesFolder: String? = null
)

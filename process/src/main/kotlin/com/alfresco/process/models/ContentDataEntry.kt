package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property name
 * @property created
 * @property createdBy
 * @property relatedContent
 * @property contentAvailable
 * @property link
 * @property mimeType
 * @property simpleType
 * @property previewStatus
 * @property thumbnailStatus
 */
@JsonClass(generateAdapter = true)
data class ContentDataEntry(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "created") @field:Json(name = "created") var created: ZonedDateTime? = null,
    @Json(name = "createdBy") @field:Json(name = "createdBy") var createdBy: UserInfo? = null,
    @Json(name = "relatedContent") @field:Json(name = "relatedContent") var relatedContent: Boolean? = null,
    @Json(name = "contentAvailable") @field:Json(name = "contentAvailable") var contentAvailable: Boolean? = null,
    @Json(name = "link") @field:Json(name = "link") var link: Boolean? = null,
    @Json(name = "mimeType") @field:Json(name = "mimeType") var mimeType: String? = null,
    @Json(name = "source") @field:Json(name = "source") var source: String? = null,
    @Json(name = "sourceId") @field:Json(name = "sourceId") var sourceId: String? = null,
    @Json(name = "simpleType") @field:Json(name = "simpleType") var simpleType: String? = null,
    @Json(name = "previewStatus") @field:Json(name = "previewStatus") var previewStatus: String? = null,
    @Json(name = "thumbnailStatus") @field:Json(name = "thumbnailStatus") var thumbnailStatus: String? = null
)

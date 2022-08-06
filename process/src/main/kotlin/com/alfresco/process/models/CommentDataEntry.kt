package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property message
 * @property created
 * @property createdBy
 */
@JsonClass(generateAdapter = true)
data class CommentDataEntry(
    @Json(name = "id") @field:Json(name = "id") var id: Int? = null,
    @Json(name = "message") @field:Json(name = "message") var message: String? = null,
    @Json(name = "created") @field:Json(name = "created") var created: ZonedDateTime? = null,
    @Json(name = "createdBy") @field:Json(name = "createdBy") var createdBy: UserInfo? = null
)

package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Marked as ResultAccountInfo
 */
@JsonClass(generateAdapter = true)
data class ResultAccountInfo(
    @Json(name = "size") @field:Json(name = "size") var size: Int? = null,
    @Json(name = "total") @field:Json(name = "total") var total: Int? = null,
    @Json(name = "start") @field:Json(name = "start") var start: Int? = null,
    @Json(name = "data") @field:Json(name = "data") var listAccountInfo: List<AccountInfo>? = emptyList()
)

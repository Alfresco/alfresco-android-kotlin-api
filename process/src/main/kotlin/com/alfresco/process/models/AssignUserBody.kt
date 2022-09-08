package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property assignee
 */
@JsonClass(generateAdapter = true)
data class AssignUserBody(
    @Json(name = "assignee") @field:Json(name = "assignee") var assignee: String
)

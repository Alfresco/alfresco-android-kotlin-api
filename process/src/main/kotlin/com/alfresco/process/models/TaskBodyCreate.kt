package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property name
 * @property priority
 * @property dueDate
 * @property description
 */
@JsonClass(generateAdapter = true)
data class TaskBodyCreate(
    @Json(name = "name") @field:Json(name = "name") var name: String,
    @Json(name = "priority") @field:Json(name = "priority") var priority: String? = null,
    @Json(name = "dueDate") @field:Json(name = "dueDate") var dueDate: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null
)

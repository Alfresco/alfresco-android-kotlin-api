package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property message
 * @property due
 * @property items
 * @property priority
 * @property reviewer
 * @property sendemailnotifications
 */
@JsonClass(generateAdapter = true)
data class ValuesModel(
    @Json(name = "message") @field:Json(name = "message") var message: String? = null,
    @Json(name = "due") @field:Json(name = "due") var due: String? = null,
    @Json(name = "items") @field:Json(name = "items") var items: String? = null,
    @Json(name = "priority") @field:Json(name = "priority") var priority: PriorityModel? = null,
    @Json(name = "reviewer") @field:Json(name = "reviewer") var reviewer: UserInfo? = null,
    @Json(name = "sendemailnotifications") @field:Json(name = "sendemailnotifications") var sendEmailNotifications: Boolean? = null
)

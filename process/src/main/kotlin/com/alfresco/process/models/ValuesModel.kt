package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property message
 * @property due
 * @property items
 * @property status
 * @property priority
 * @property reviewer
 * @property reviewGroups
 * @property sendEmailNotifications
 * @property comment
 */
@JsonClass(generateAdapter = true)
data class ValuesModel(
    @Json(name = "message") @field:Json(name = "message") var message: String? = null,
    @Json(name = "due") @field:Json(name = "due") var due: String? = null,
    @Json(name = "items") @field:Json(name = "items") var items: String? = null,
    @Json(name = "priority") @field:Json(name = "priority") var priority: CommonOptionModel? = null,
    @Json(name = "status") @field:Json(name = "status") var status: CommonOptionModel? = null,
    @Json(name = "reviewer") @field:Json(name = "reviewer") var reviewer: UserInfo? = null,
    @Json(name = "reviewgroups") @field:Json(name = "reviewgroups") var reviewGroups: GroupInfo? = null,
    @Json(name = "sendemailnotifications") @field:Json(name = "sendemailnotifications") var sendEmailNotifications: Boolean? = null,
    @Json(name = "comment") @field:Json(name = "comment") var comment: String? = null
)

/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * @property id
 * @property firstName
 * @property lastName
 * @property displayName
 * @property description
 * @property avatarId
 * @property email
 * @property skypeId
 * @property googleId
 * @property instantMessageId
 * @property jobTitle
 * @property location
 * @property company
 * @property mobile
 * @property telephone
 * @property statusUpdatedAt
 * @property userStatus
 * @property enabled
 * @property emailNotificationsEnabled
 * @property aspectNames
 * @property properties
 * @property capabilities
 */
@JsonClass(generateAdapter = true)
data class Person(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "firstName") @field:Json(name = "firstName") var firstName: String,
    @Json(name = "email") @field:Json(name = "email") var email: String,
    @Json(name = "enabled") @field:Json(name = "enabled") var enabled: Boolean,
    @Json(name = "lastName") @field:Json(name = "lastName") var lastName: String? = null,
    @Json(name = "displayName") @field:Json(name = "displayName") var displayName: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "avatarId") @field:Json(name = "avatarId") var avatarId: String? = null,
    @Json(name = "skypeId") @field:Json(name = "skypeId") var skypeId: String? = null,
    @Json(name = "googleId") @field:Json(name = "googleId") var googleId: String? = null,
    @Json(name = "instantMessageId") @field:Json(name = "instantMessageId") var instantMessageId: String? = null,
    @Json(name = "jobTitle") @field:Json(name = "jobTitle") var jobTitle: String? = null,
    @Json(name = "location") @field:Json(name = "location") var location: String? = null,
    @Json(name = "company") @field:Json(name = "company") var company: Company? = null,
    @Json(name = "mobile") @field:Json(name = "mobile") var mobile: String? = null,
    @Json(name = "telephone") @field:Json(name = "telephone") var telephone: String? = null,
    @Json(name = "statusUpdatedAt") @field:Json(name = "statusUpdatedAt") var statusUpdatedAt: ZonedDateTime? = null,
    @Json(name = "userStatus") @field:Json(name = "userStatus") var userStatus: String? = null,
    @Json(name = "emailNotificationsEnabled") @field:Json(name = "emailNotificationsEnabled") var emailNotificationsEnabled: Boolean? = null,
    @Json(name = "aspectNames") @field:Json(name = "aspectNames") var aspectNames: List<String>? = null,
    @Json(name = "properties") @field:Json(name = "properties") var properties: Map<String, Any?>? = null,
    @Json(name = "capabilities") @field:Json(name = "capabilities") var capabilities: Map<String, Any?>? = null
)

/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property createdAt
 * @property site
 * @property person
 * @property message
 */
@JsonClass(generateAdapter = true)
data class SiteMembershipRequestWithPerson(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "createdAt") @field:Json(name = "createdAt") var createdAt: ZonedDateTime,
    @Json(name = "site") @field:Json(name = "site") var site: Site,
    @Json(name = "person") @field:Json(name = "person") var person: Person,
    @Json(name = "message") @field:Json(name = "message") var message: String? = null
)

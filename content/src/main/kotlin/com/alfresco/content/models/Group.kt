/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property displayName
 * @property isRoot
 * @property parentIds
 * @property zones
 */
@JsonClass(generateAdapter = true)
data class Group(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "displayName") @field:Json(name = "displayName") var displayName: String,
    @Json(name = "isRoot") @field:Json(name = "isRoot") var isRoot: Boolean,
    @Json(name = "parentIds") @field:Json(name = "parentIds") var parentIds: List<String>? = null,
    @Json(name = "zones") @field:Json(name = "zones") var zones: List<String>? = null
)

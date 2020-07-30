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
 * @property nodeId
 * @property expiresAt
 */
@JsonClass(generateAdapter = true)
data class SharedLinkBodyCreate(
    @Json(name = "nodeId") @field:Json(name = "nodeId") var nodeId: String,
    @Json(name = "expiresAt") @field:Json(name = "expiresAt") var expiresAt: ZonedDateTime? = null
)
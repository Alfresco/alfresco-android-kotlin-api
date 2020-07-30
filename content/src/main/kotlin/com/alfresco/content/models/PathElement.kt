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
 * @property name
 * @property nodeType
 * @property aspectNames
 */
@JsonClass(generateAdapter = true)
data class PathElement(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "nodeType") @field:Json(name = "nodeType") var nodeType: String? = null,
    @Json(name = "aspectNames") @field:Json(name = "aspectNames") var aspectNames: List<String>? = null
)
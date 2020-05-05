/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property pagination
 * @property entries
 */
@JsonClass(generateAdapter = true)
data class DeletedNodesPagingList(
    @Json(name = "pagination") @field:Json(name = "pagination") var pagination: Pagination? = null,
    @Json(name = "entries") @field:Json(name = "entries") var entries: List<DeletedNodeEntry>? = null
)

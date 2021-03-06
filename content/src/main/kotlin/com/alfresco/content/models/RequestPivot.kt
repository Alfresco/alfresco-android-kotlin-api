/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A list of pivots.
 * @property key A key corresponding to a matching field facet label or stats.
 * @property pivots
 */
@JsonClass(generateAdapter = true)
data class RequestPivot(
    @Json(name = "key") @field:Json(name = "key") var key: String? = null,
    @Json(name = "pivots") @field:Json(name = "pivots") var pivots: List<RequestPivot>? = null
)

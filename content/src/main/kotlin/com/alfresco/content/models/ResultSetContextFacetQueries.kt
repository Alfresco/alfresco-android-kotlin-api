/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property label
 * @property filterQuery The filter query you can use to apply this facet
 * @property count
 */
@JsonClass(generateAdapter = true)
data class ResultSetContextFacetQueries(
    @Json(name = "label") @field:Json(name = "label") var label: String? = null,
    @Json(name = "filterQuery") @field:Json(name = "filterQuery") var filterQuery: String? = null,
    @Json(name = "count") @field:Json(name = "count") var count: Int? = null
)

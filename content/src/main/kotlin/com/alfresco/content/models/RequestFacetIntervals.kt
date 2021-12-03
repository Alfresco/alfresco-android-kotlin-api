/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Facet Intervals
 * @property sets Sets the intervals for all fields.
 * @property intervals Specifies the fields to facet by interval.
 */
@JsonClass(generateAdapter = true)
data class RequestFacetIntervals(
    @Json(name = "sets") @field:Json(name = "sets") var sets: List<RequestFacetSet>? = null,
    @Json(name = "intervals") @field:Json(name = "intervals") var intervals: List<RequestFacetIntervalsInIntervals>? = null
)

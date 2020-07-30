/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A metric used in faceting
 * @property type The type of metric, e.g. count
 * @property value The metric value, e.g. {\&quot;count\&quot;: 34} 
 */
@JsonClass(generateAdapter = true)
data class GenericMetric(
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "value") @field:Json(name = "value") var value: Map<String, Any?>? = null
)
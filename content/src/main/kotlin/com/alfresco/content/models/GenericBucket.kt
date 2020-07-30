/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A bucket of facet results
 * @property label The bucket label
 * @property filterQuery The filter query you can use to apply this facet
 * @property display An optional field for additional display information
 * @property metrics An array of buckets and values
 * @property facets Additional list of nested facets
 * @property bucketInfo
 */
@JsonClass(generateAdapter = true)
data class GenericBucket(
    @Json(name = "label") @field:Json(name = "label") var label: String? = null,
    @Json(name = "filterQuery") @field:Json(name = "filterQuery") var filterQuery: String? = null,
    @Json(name = "display") @field:Json(name = "display") var display: Map<String, Any?>? = null,
    @Json(name = "metrics") @field:Json(name = "metrics") var metrics: List<GenericMetric>? = null,
    @Json(name = "facets") @field:Json(name = "facets") var facets: List<Map<String, Any?>>? = null,
    @Json(name = "bucketInfo") @field:Json(name = "bucketInfo") var bucketInfo: GenericBucketBucketInfo? = null
)
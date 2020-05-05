/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Query.
 * @property language The query language in which the query is written.
 * @property userQuery The exact search request typed in by the user
 * @property query The query which may have been generated in some way from the userQuery
 */
@JsonClass(generateAdapter = true)
data class RequestQuery(
    @Json(name = "query") @field:Json(name = "query") var query: String,
    @Json(name = "language") @field:Json(name = "language") var language: RequestQuery.LanguageEnum? = null,
    @Json(name = "userQuery") @field:Json(name = "userQuery") var userQuery: String? = null
) {
    /**
     * The query language in which the query is written.
     * Values: AFTS, LUCENE, CMIS
     */
    @JsonClass(generateAdapter = false)
    enum class LanguageEnum(val value: String) {
        @Json(name = "afts") AFTS("afts"),
        @Json(name = "lucene") LUCENE("lucene"),
        @Json(name = "cmis") CMIS("cmis")
    }
}

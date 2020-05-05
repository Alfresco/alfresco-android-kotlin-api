/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property query
 * @property paging
 * @property include
 * @property includeRequest When true, include the original request in the response
 * @property fields
 * @property sort
 * @property templates
 * @property defaults
 * @property localization
 * @property filterQueries
 * @property facetQueries
 * @property facetFields
 * @property facetIntervals
 * @property pivots
 * @property stats
 * @property spellcheck
 * @property scope
 * @property limits
 * @property highlight
 * @property ranges
 */
@JsonClass(generateAdapter = true)
data class SearchRequest(
    @Json(name = "query") @field:Json(name = "query") var query: RequestQuery,
    @Json(name = "paging") @field:Json(name = "paging") var paging: RequestPagination? = null,
    @Json(name = "include") @field:Json(name = "include") var include: RequestInclude? = null,
    @Json(name = "includeRequest") @field:Json(name = "includeRequest") var includeRequest: Boolean? = null,
    @Json(name = "fields") @field:Json(name = "fields") var fields: RequestFields? = null,
    @Json(name = "sort") @field:Json(name = "sort") var sort: RequestSortDefinition? = null,
    @Json(name = "templates") @field:Json(name = "templates") var templates: RequestTemplates? = null,
    @Json(name = "defaults") @field:Json(name = "defaults") var defaults: RequestDefaults? = null,
    @Json(name = "localization") @field:Json(name = "localization") var localization: RequestLocalization? = null,
    @Json(name = "filterQueries") @field:Json(name = "filterQueries") var filterQueries: RequestFilterQueries? = null,
    @Json(name = "facetQueries") @field:Json(name = "facetQueries") var facetQueries: RequestFacetQueries? = null,
    @Json(name = "facetFields") @field:Json(name = "facetFields") var facetFields: RequestFacetFields? = null,
    @Json(name = "facetIntervals") @field:Json(name = "facetIntervals") var facetIntervals: RequestFacetIntervals? = null,
    @Json(name = "pivots") @field:Json(name = "pivots") var pivots: List<RequestPivot>? = null,
    @Json(name = "stats") @field:Json(name = "stats") var stats: List<RequestStats>? = null,
    @Json(name = "spellcheck") @field:Json(name = "spellcheck") var spellcheck: RequestSpellcheck? = null,
    @Json(name = "scope") @field:Json(name = "scope") var scope: RequestScope? = null,
    @Json(name = "limits") @field:Json(name = "limits") var limits: RequestLimits? = null,
    @Json(name = "highlight") @field:Json(name = "highlight") var highlight: RequestHighlight? = null,
    @Json(name = "ranges") @field:Json(name = "ranges") var ranges: List<RequestRange>? = null
)

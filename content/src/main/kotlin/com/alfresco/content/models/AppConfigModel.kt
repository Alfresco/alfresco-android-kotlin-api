package com.alfresco.content.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Advance search model
 * @property search
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class AppConfigModel(
    @Json(name = "search") @field:Json(name = "search") val search: List<SearchItem>?
) : Parcelable

/**
 * Categories model
 * @property expanded
 * @property component
 * @property name
 * @property id
 * @property enabled
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class CategoriesItem(
    @Json(name = "expanded") @field:Json(name = "expanded") val expanded: Boolean?,
    @Json(name = "component") @field:Json(name = "component") val component: Component?,
    @Json(name = "name") @field:Json(name = "name") val name: String?,
    @Json(name = "id") @field:Json(name = "id") val id: String?,
    @Json(name = "enabled") @field:Json(name = "enabled") val enabled: Boolean?
) : Parcelable

/**
 * Component model
 * @property settings
 * @property selector
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Component(
    @Json(name = "settings") @field:Json(name = "settings") val settings: Settings?,
    @Json(name = "selector") @field:Json(name = "selector") val selector: String?
) : Parcelable

/**
 * SearchItem model
 * @property default
 * @property name
 * @property filterWithContains
 * @property categories
 * @property resetButton
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class SearchItem(
    @Json(name = "default") @field:Json(name = "default") val default: Boolean?,
    @Json(name = "name") @field:Json(name = "name") val name: String?,
    @Json(name = "filterWithContains") @field:Json(name = "filterWithContains") val filterWithContains: Boolean?,
    @Json(name = "categories") @field:Json(name = "categories") val categories: List<CategoriesItem>?,
    @Json(name = "resetButton") @field:Json(name = "resetButton") val resetButton: Boolean?,
    @Json(name = "filterQueries") @field:Json(name = "filterQueries") val filterQueries: List<FilterQueriesItem>?
) : Parcelable

/**
 * Filter Queries Model
 * @property query
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class FilterQueriesItem(@Json(name = "query") @field:Json(name = "query") val query: String?) : Parcelable

/**
 * Settings model
 * @property field
 * @property pattern
 * @property placeholder
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Settings(
    @Json(name = "field") @field:Json(name = "field") val field: String?,
    @Json(name = "pattern") @field:Json(name = "pattern") val pattern: String?,
    @Json(name = "placeholder") @field:Json(name = "placeholder") val placeholder: String?,
    @Json(name = "pageSize") @field:Json(name = "pageSize") val pageSize: Int?,
    @Json(name = "operator") @field:Json(name = "operator") val operator: String?,
    @Json(name = "options") @field:Json(name = "options") val options: List<Options>?,
    @Json(name = "min") @field:Json(name = "min") val min: Int?,
    @Json(name = "max") @field:Json(name = "max") val max: Int?,
    @Json(name = "step") @field:Json(name = "step") val step: Int?,
    @Json(name = "thumbLabel") @field:Json(name = "thumbLabel") val thumbLabel: Boolean?,
    @Json(name = "format") @field:Json(name = "format") val format: String?,
    @Json(name = "dateFormat") @field:Json(name = "dateFormat") val dateFormat: String?,
    @Json(name = "maxDate") @field:Json(name = "maxDate") val maxDate: String?
) : Parcelable

/**
 * Options model
 * @property name
 * @property value
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Options(
    @Json(name = "name") @field:Json(name = "name") val name: String?,
    @Json(name = "value") @field:Json(name = "value") val value: String?,
    @Json(name = "default") @field:Json(name = "default") val default: Boolean?
) : Parcelable

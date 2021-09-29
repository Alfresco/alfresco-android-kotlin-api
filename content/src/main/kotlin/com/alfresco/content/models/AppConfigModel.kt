package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Advance search model
 * @property search
 */
@JsonClass(generateAdapter = true)
data class AppConfigModel(
    @Json(name = "search") @field:Json(name = "search") val search: List<SearchItem>?)

/**
 * Categories model
 * @property expanded
 * @property component
 * @property name
 * @property id
 * @property enabled
 */
@JsonClass(generateAdapter = true)
data class CategoriesItem(
    @Json(name = "expanded") @field:Json(name = "expanded") val expanded: Boolean = false,
    @Json(name = "component") @field:Json(name = "component") val component: Component,
    @Json(name = "name") @field:Json(name = "name") val name: String = "",
    @Json(name = "id") @field:Json(name = "id") val id: String = "",
    @Json(name = "enabled") @field:Json(name = "enabled") val enabled: Boolean = false
)

/**
 * Component model
 * @property settings
 * @property selector
 */
@JsonClass(generateAdapter = true)
data class Component(
    @Json(name = "settings") @field:Json(name = "settings") val settings: Settings,
    @Json(name = "selector") @field:Json(name = "selector") val selector: String = ""
)

/**
 * SearchItem model
 * @property default
 * @property name
 * @property filterWithContains
 * @property categories
 * @property resetButton
 */
@JsonClass(generateAdapter = true)
data class SearchItem(
    @Json(name = "default") @field:Json(name = "default") val default: Boolean = false,
    @Json(name = "name") @field:Json(name = "name") val name: String = "",
    @Json(name = "filterWithContains") @field:Json(name = "filterWithContains") val filterWithContains: Boolean = false,
    @Json(name = "categories") @field:Json(name = "categories") val categories: List<CategoriesItem>?,
    @Json(name = "resetButton") @field:Json(name = "resetButton") val resetButton: Boolean = false
)

/**
 * Settings model
 * @property field
 * @property pattern
 * @property placeholder
 */
@JsonClass(generateAdapter = true)
data class Settings(
    @Json(name = "field") @field:Json(name = "field") val field: String = "",
    @Json(name = "pattern") @field:Json(name = "pattern") val pattern: String = "",
    @Json(name = "placeholder") @field:Json(name = "placeholder") val placeholder: String = ""
)
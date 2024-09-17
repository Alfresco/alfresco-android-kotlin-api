package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @param featuresMobile
 */

@JsonClass(generateAdapter = true)
data class MobileConfigData(
    @Json(name = "features_mobile") @field:Json(name = "features_mobile") var featuresMobile: Features? = null,
)

/**
 * @param dynamicMenus
 */
@JsonClass(generateAdapter = true)
data class Features(
    @Json(name = "menu") @field:Json(name = "menu") var dynamicMenus: List<DynamicMenu>? = null,
)

/**
 * @param id
 * @param name
 * @param enabled
 */
@JsonClass(generateAdapter = true)
data class DynamicMenu(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "enabled") @field:Json(name = "enabled") var enabled: Boolean? = null,
)

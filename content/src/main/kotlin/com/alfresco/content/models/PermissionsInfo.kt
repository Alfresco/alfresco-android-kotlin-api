/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property isInheritanceEnabled
 * @property inherited
 * @property locallySet
 * @property settable
 */
@JsonClass(generateAdapter = true)
data class PermissionsInfo(
    @Json(name = "isInheritanceEnabled") @field:Json(name = "isInheritanceEnabled") var isInheritanceEnabled: Boolean? = null,
    @Json(name = "inherited") @field:Json(name = "inherited") var inherited: List<PermissionElement>? = null,
    @Json(name = "locallySet") @field:Json(name = "locallySet") var locallySet: List<PermissionElement>? = null,
    @Json(name = "settable") @field:Json(name = "settable") var settable: List<String>? = null
)

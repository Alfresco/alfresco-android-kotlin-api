/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property message
 */
@JsonClass(generateAdapter = true)
data class SiteMembershipRequestBodyUpdate(
    @Json(name = "message") @field:Json(name = "message") var message: String? = null
)

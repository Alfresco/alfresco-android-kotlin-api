/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property actionDefinitionId
 * @property targetId The entity upon which to execute the action, typically a node ID or similar.
 * @property params
 */
@JsonClass(generateAdapter = true)
data class ActionBodyExec(
    @Json(name = "actionDefinitionId") @field:Json(name = "actionDefinitionId") var actionDefinitionId: String,
    @Json(name = "targetId") @field:Json(name = "targetId") var targetId: String? = null,
    @Json(name = "params") @field:Json(name = "params") var params: Map<String, Any?>? = null
)
/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property targetParentId
 * @property name The name must not contain spaces or the following special characters: * \&quot; &lt; &gt; \\ / ? : and |. The character . must not be used at the end of the name. 
 */
@JsonClass(generateAdapter = true)
data class NodeBodyMove(
    @Json(name = "targetParentId") @field:Json(name = "targetParentId") var targetParentId: String,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null
)

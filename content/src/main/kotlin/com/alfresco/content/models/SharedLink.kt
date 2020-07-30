/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

/**
 * @property id
 * @property expiresAt
 * @property nodeId
 * @property name The name must not contain spaces or the following special characters: * \&quot; &lt; &gt; \\ / ? : and |.  The character . must not be used at the end of the name. 
 * @property title
 * @property description
 * @property modifiedAt
 * @property modifiedByUser
 * @property sharedByUser
 * @property content
 * @property allowableOperations The allowable operations for the Quickshare link itself. See allowableOperationsOnTarget for the allowable operations pertaining to the linked content node. 
 * @property allowableOperationsOnTarget The allowable operations for the content node being shared. 
 * @property isFavorite
 * @property properties A subset of the target node&#39;s properties, system properties and properties already available in the SharedLink are excluded. 
 * @property aspectNames
 */
@JsonClass(generateAdapter = true)
data class SharedLink(
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "expiresAt") @field:Json(name = "expiresAt") var expiresAt: ZonedDateTime? = null,
    @Json(name = "nodeId") @field:Json(name = "nodeId") var nodeId: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "title") @field:Json(name = "title") var title: String? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "modifiedAt") @field:Json(name = "modifiedAt") var modifiedAt: ZonedDateTime? = null,
    @Json(name = "modifiedByUser") @field:Json(name = "modifiedByUser") var modifiedByUser: UserInfo? = null,
    @Json(name = "sharedByUser") @field:Json(name = "sharedByUser") var sharedByUser: UserInfo? = null,
    @Json(name = "content") @field:Json(name = "content") var content: ContentInfo? = null,
    @Json(name = "allowableOperations") @field:Json(name = "allowableOperations") var allowableOperations: List<String>? = null,
    @Json(name = "allowableOperationsOnTarget") @field:Json(name = "allowableOperationsOnTarget") var allowableOperationsOnTarget: List<String>? = null,
    @Json(name = "isFavorite") @field:Json(name = "isFavorite") var isFavorite: Boolean? = null,
    @Json(name = "properties") @field:Json(name = "properties") var properties: Map<String, Any?>? = null,
    @Json(name = "aspectNames") @field:Json(name = "aspectNames") var aspectNames: List<String>? = null
)
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
 * @property name The name must not contain spaces or the following special characters: * \&quot; &lt; &gt; \\ / ? : and |. The character . must not be used at the end of the name. 
 * @property nodeType
 * @property isFolder
 * @property isFile
 * @property isLocked
 * @property modifiedAt
 * @property modifiedByUser
 * @property createdAt
 * @property createdByUser
 * @property parentId
 * @property isLink
 * @property isFavorite
 * @property content
 * @property aspectNames
 * @property properties
 * @property allowableOperations
 * @property path
 * @property permissions
 * @property association
 */
@JsonClass(generateAdapter = true)
data class NodeChildAssociation(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "name") @field:Json(name = "name") var name: String,
    @Json(name = "nodeType") @field:Json(name = "nodeType") var nodeType: String,
    @Json(name = "isFolder") @field:Json(name = "isFolder") var isFolder: Boolean,
    @Json(name = "isFile") @field:Json(name = "isFile") var isFile: Boolean,
    @Json(name = "modifiedAt") @field:Json(name = "modifiedAt") var modifiedAt: ZonedDateTime,
    @Json(name = "modifiedByUser") @field:Json(name = "modifiedByUser") var modifiedByUser: UserInfo,
    @Json(name = "createdAt") @field:Json(name = "createdAt") var createdAt: ZonedDateTime,
    @Json(name = "createdByUser") @field:Json(name = "createdByUser") var createdByUser: UserInfo,
    @Json(name = "isLocked") @field:Json(name = "isLocked") var isLocked: Boolean? = null,
    @Json(name = "parentId") @field:Json(name = "parentId") var parentId: String? = null,
    @Json(name = "isLink") @field:Json(name = "isLink") var isLink: Boolean? = null,
    @Json(name = "isFavorite") @field:Json(name = "isFavorite") var isFavorite: Boolean? = null,
    @Json(name = "content") @field:Json(name = "content") var content: ContentInfo? = null,
    @Json(name = "aspectNames") @field:Json(name = "aspectNames") var aspectNames: List<String>? = null,
    @Json(name = "properties") @field:Json(name = "properties") var properties: Map<String, Any?>? = null,
    @Json(name = "allowableOperations") @field:Json(name = "allowableOperations") var allowableOperations: List<String>? = null,
    @Json(name = "path") @field:Json(name = "path") var path: PathInfo? = null,
    @Json(name = "permissions") @field:Json(name = "permissions") var permissions: PermissionsInfo? = null,
    @Json(name = "association") @field:Json(name = "association") var association: ChildAssociationInfo? = null
)

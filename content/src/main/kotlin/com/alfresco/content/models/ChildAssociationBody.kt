/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property childId
 * @property assocType
 */
@JsonClass(generateAdapter = true)
data class ChildAssociationBody(
    @Json(name = "childId") @field:Json(name = "childId") var childId: String,
    @Json(name = "assocType") @field:Json(name = "assocType") var assocType: String
)

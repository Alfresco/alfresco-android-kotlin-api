/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property displayName
 * @property memberType
 */
@JsonClass(generateAdapter = true)
data class GroupMember(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "displayName") @field:Json(name = "displayName") var displayName: String,
    @Json(name = "memberType") @field:Json(name = "memberType") var memberType: GroupMember.MemberTypeEnum
) {
    /**
     * Values: GROUP, PERSON
     */
    @JsonClass(generateAdapter = false)
    enum class MemberTypeEnum(val value: String) {
        @Json(name = "GROUP") GROUP("GROUP"),
        @Json(name = "PERSON") PERSON("PERSON")
    }
}
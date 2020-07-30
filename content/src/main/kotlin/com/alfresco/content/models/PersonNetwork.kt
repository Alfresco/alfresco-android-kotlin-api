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
 * A network is the group of users and sites that belong to an organization. Networks are organized by email domain. When a user signs up for an Alfresco account , their email domain becomes their Home Network. 
 * @property id This network&#39;s unique id
 * @property homeNetwork Is this the home network?
 * @property isEnabled
 * @property createdAt
 * @property paidNetwork
 * @property subscriptionLevel
 * @property quotas
 */
@JsonClass(generateAdapter = true)
data class PersonNetwork(
    @Json(name = "id") @field:Json(name = "id") var id: String,
    @Json(name = "isEnabled") @field:Json(name = "isEnabled") var isEnabled: Boolean,
    @Json(name = "homeNetwork") @field:Json(name = "homeNetwork") var homeNetwork: Boolean? = null,
    @Json(name = "createdAt") @field:Json(name = "createdAt") var createdAt: ZonedDateTime? = null,
    @Json(name = "paidNetwork") @field:Json(name = "paidNetwork") var paidNetwork: Boolean? = null,
    @Json(name = "subscriptionLevel") @field:Json(name = "subscriptionLevel") var subscriptionLevel: PersonNetwork.SubscriptionLevelEnum? = null,
    @Json(name = "quotas") @field:Json(name = "quotas") var quotas: List<NetworkQuota>? = null
) {
    /**
     * Values: FREE, STANDARD, ENTERPRISE
     */
    @JsonClass(generateAdapter = false)
    enum class SubscriptionLevelEnum(val value: String) {
        @Json(name = "Free") FREE("Free"),
        @Json(name = "Standard") STANDARD("Standard"),
        @Json(name = "Enterprise") ENTERPRISE("Enterprise")
    }
}
package com.alfresco.process.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fields(
    @Json(name = "fieldType") @field:Json(name = "fieldType") var fieldType: String? = null,
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "message") @field:Json(name = "message") var message: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "value") @field:Json(name = "value") var value: String? = null,
    @Json(name = "required") @field:Json(name = "required") var required: Boolean? = null,
    @Json(name = "readOnly") @field:Json(name = "readOnly") var readOnly: Boolean? = null,
    @Json(name = "overrideId") @field:Json(name = "overrideId") var overrideId: Boolean? = null,
    @Json(name = "fields") @field:Json(name = "fields") var fields: Map<String, Any>? = null
) {
    fun getFieldMapAsList() = fields?.values?.map { (it as List<Fields>) }?.flatten()
}

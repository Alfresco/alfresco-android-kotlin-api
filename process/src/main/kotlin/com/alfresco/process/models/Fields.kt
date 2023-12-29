package com.alfresco.process.models

import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject

/**
 * @property fieldType
 * @property id
 * @property name
 * @property message
 * @property type
 * @property value
 * @property required
 * @property readOnly
 * @property overrideId
 * @property fields
 */
@JsonClass(generateAdapter = true)
data class Fields(
    @Json(name = "fieldType") @field:Json(name = "fieldType") var fieldType: String? = null,
    @Json(name = "id") @field:Json(name = "id") var id: String? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "message") @field:Json(name = "message") var message: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "placeholder") @field:Json(name = "placeholder") var placeholder: String? = null,
    @Json(name = "value") @field:Json(name = "value") var value: Any? = null,
    @Json(name = "minLength") @field:Json(name = "minLength") var minLength: Int = 0,
    @Json(name = "maxLength") @field:Json(name = "maxLength") var maxLength: Int = 0,
    @Json(name = "minValue") @field:Json(name = "minValue") var minValue: String? = null,
    @Json(name = "maxValue") @field:Json(name = "maxValue") var maxValue: String? = null,
    @Json(name = "regexPattern") @field:Json(name = "regexPattern") var regexPattern: String? = null,
    @Json(name = "required") @field:Json(name = "required") var required: Boolean? = null,
    @Json(name = "readOnly") @field:Json(name = "readOnly") var readOnly: Boolean? = null,
    @Json(name = "overrideId") @field:Json(name = "overrideId") var overrideId: Boolean? = null,
    @Json(name = "options") @field:Json(name = "options") var options: List<Options>? = null,
    @Json(name = "fields") @field:Json(name = "fields") var fields: Map<String, Any>? = null
) {
    /**
     * returns Map fields as List
     */
    fun getFieldMapAsList() = fields?.values?.map { it as List<*> }?.flatten()?.map { Gson().fromJson(JSONObject(it as Map<String, Fields>).toString(), Fields::class.java) }
}

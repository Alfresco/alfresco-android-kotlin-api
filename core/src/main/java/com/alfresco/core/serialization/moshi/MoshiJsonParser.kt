package com.alfresco.core.serialization.moshi

import com.alfresco.core.serialization.JsonParser
import com.squareup.moshi.Moshi


/**
 * Specific implementation of a [JsonParser] interface
 *
 * Created by Bogdan Roatis on 03 April 2019.
 */
object MoshiJsonParser : JsonParser() {

    private val moshi = Moshi.Builder().build()

    override fun <T> fromJson(response: String, clazz: Class<T>): T? =
            moshi.adapter(clazz).fromJson(response)

    override fun <T> toJson(entity: T?, clazz: Class<T>): String =
            moshi.adapter(clazz).toJson(entity)
}

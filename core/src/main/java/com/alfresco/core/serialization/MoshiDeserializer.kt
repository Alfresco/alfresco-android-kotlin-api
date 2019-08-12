package com.alfresco.core.serialization

import com.squareup.moshi.Moshi


/**
 * Specific implementation of a [Deserializer] interface
 *
 * Created by Bogdan Roatis on 03 April 2019.
 */
class MoshiDeserializer : Deserializer {

    private val moshi = Moshi.Builder().build()

    override fun <T : Any> deserialize(response: String, clazz: Class<T>): T? {
        val jsonAdapter = moshi.adapter(clazz)
        return jsonAdapter.fromJson(response)
    }

    override fun <T : Any> serialize(entity: T, clazz: Class<T>): String? {
        val jsonAdapter = moshi.adapter(clazz)
        return jsonAdapter.toJson(entity)
    }
}

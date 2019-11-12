package com.alfresco.core.serialization

/**
 *
 * Created by Bogdan Roatis on 27 September 2019.
 */
abstract class JsonParser {

    /**
     * Deserialize a json string into [T]
     *
     * @param response the json string that needs to be deserialized
     * @param clazz the type of object that we want the [response] to be deserialized in
     *
     * @return [T] the instance of [T]
     */
    protected abstract fun <T> fromJson(response: String, clazz: Class<T>): T?

    /**
     * Serialize the [T] into a string
     *
     * @param entity the [T] that needs to be serialized
     * @param clazz the class of the [entity]
     *
     * @return a string that represents the serialized [entity]
     */
    protected abstract fun <T> toJson(entity: T?, clazz: Class<T>): String

    inline fun <reified T> fromJson(json: String): T? =
            fromJson(json, T::class.java)

    inline fun <reified T> toJson(entity: T): String =
            toJson(entity, T::class.java)
}

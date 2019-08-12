package com.alfresco.core.serialization

/**
 * An interface for deserializers
 *
 * Created by Bogdan Roatis on 4/2/2019.
 */
interface Deserializer {

    /**
     * Deserialize a json string into [T]
     *
     * @param response the json string that needs to be deserialized
     * @param clazz the type of object that we want the [response] to be deserialized in
     *
     * @return [T] the instance of [T]
     */
    fun <T : Any> deserialize(response: String, clazz: Class<T>): T?

    /**
     * Serialize the [T] into a string
     *
     * @param entity the [T] that needs to be serialized
     * @param clazz the class of the [entity]
     *
     * @return a string that represents the serialized [entity]
     */
    fun <T : Any> serialize(entity: T, clazz: Class<T>): String?
}

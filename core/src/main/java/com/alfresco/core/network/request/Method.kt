package com.alfresco.core.network.request

/**
 * Some of the HTTP methods as defined by RFC 7231
 *
 * @see [RFC 7231](https://tools.ietf.org/html/rfc7231)
 *
 * Created by Bogdan Roatis on 03 April 2019.
 */
enum class Method(val value: String) {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE")
}

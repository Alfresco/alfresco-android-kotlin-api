package com.alfresco.core.network.request

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.alfresco.core.data.AlfrescoResponse
import com.alfresco.core.data.BasicResponse
import com.alfresco.core.network.NetworkConfigManager
import com.alfresco.core.network.contract.Result

/**
 * Convenient and network extensions for the [Request] object
 *
 * Created by Bogdan Roatis on 03 April 2019.
 */

/**
 * Initiates the call on the give [Request]
 *
 * @return the response from the server parsed to [AlfrescoResponse]
 *          and wrapped inside a [Result]
 */
suspend fun Request.response() =
        call(this)

/**
 * Initiates the call on the give [Request]
 *
 * @return the response from the server parsed to a Json string
 *          and wrapped inside a [Result]
 */
suspend fun Request.responseJsonString() =
        call(this).mapDataTo {
            NetworkConfigManager.deserializer.serialize(this, AlfrescoResponse::class.java)
        }

/**
 * Initiates the call on the give [Request]
 *
 * @return the response from the server parsed to the unmodified string body
 *          and wrapped inside a [Result]
 */
suspend fun Request.responseString() =
        call(this).mapDataTo { body }

/**
 * Initiates the call on the give [Request]
 *
 * @return the response from the server parsed to a [T] entity
 *          and wrapped inside a [Result]
 */
suspend inline fun <reified T : BasicResponse> Request.responseCustom() =
        call(this).mapDataTo {
            body?.let {
                NetworkConfigManager.deserializer.deserialize(it, T::class.java) as T
            } ?: throw UnsupportedOperationException()
        }

/**
 * Hidden function for initiating the call
 */
suspend fun call(request: Request): Result<AlfrescoResponse, Throwable> {
    return request.run {
        when (method) {
            Method.GET -> {
                NetworkConfigManager.alfrescoNetworkProtocol.get(
                        url = url.toString(),
                        headers = headers,
                        params = params,
                        timeout = NetworkConfigManager.DEFAULT_TIMEOUT)
            }
            Method.POST -> {
                NetworkConfigManager.alfrescoNetworkProtocol.post(
                        url = url.toString(),
                        headers = headers,
                        params = params,
                        data = body,
                        timeout = NetworkConfigManager.DEFAULT_TIMEOUT)
            }
            Method.PUT -> TODO()
            Method.DELETE -> TODO()
        }
    }
}

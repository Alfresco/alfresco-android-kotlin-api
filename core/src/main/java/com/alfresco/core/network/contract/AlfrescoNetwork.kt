package com.alfresco.core.network.contract

import com.alfresco.core.data.AlfrescoResponse

/**
 * An abstraction for every networking implementation
 * that will have to be added in the future
 *
 * Created by Bogdan Roatis on 3/6/2019.
 */
interface AlfrescoNetwork {

    suspend fun get(url: String,
                    headers: Map<String, String> = mapOf(),
                    params: Map<String, String> = mapOf(),
                    data: Any? = null,
                    timeout: Long): Result<AlfrescoResponse, Throwable>

    suspend fun post(url: String,
                     headers: Map<String, String> = mapOf(),
                     params: Map<String, String> = mapOf(),
                     data: Any? = null,
                     timeout: Long): Result<AlfrescoResponse, Throwable>
}

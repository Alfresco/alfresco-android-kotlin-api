package com.alfresco.core.network.contract.implementation

import com.alfresco.core.data.AlfrescoResponse
import com.alfresco.core.data.toAlfrescoResponse
import com.alfresco.core.network.contract.AlfrescoNetwork
import com.alfresco.core.network.contract.Response
import com.alfresco.core.network.contract.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

/**
 * Created by Bogdan Roatis on 3/22/2019.
 */
class OkHttpNetwork : AlfrescoNetwork {

    override suspend fun get(url: String,
                             headers: Map<String, String>,
                             params: Map<String, String>,
                             data: Any?,
                             timeout: Long): Result<AlfrescoResponse, Throwable> {

        val client = getHttpClient(timeout)

        //TODO add data

        val request = Request.Builder().url(url).get()

        headers.forEach {
            request.addHeader(it.key, it.value)
        }

        return response(client, request)
    }

    override suspend fun post(url: String,
                              headers: Map<String, String>,
                              params: Map<String, String>,
                              data: Any?,
                              timeout: Long): Result<AlfrescoResponse, Throwable> {

        val client = getHttpClient(timeout)

        val request = Request.Builder().url(url)

        data?.let {
            //TODO this is prone to errors
            //TODO should check the instance then convert
            val body = (data as String).toRequestBody(getMediaType(headers))
            request.post(body)
        }

        headers.forEach {
            request.addHeader(it.key, it.value)
        }

        return response(client, request)
    }

    private fun getMediaType(headers: Map<String, String?>) =
            (headers["Content-Type"]
                    ?: error("Content-Type not found in headers")).toMediaTypeOrNull()

    private fun getHttpClient(timeout: Long) =
            OkHttpClient.Builder()
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .build()

    private suspend fun response(client: OkHttpClient, request: Request.Builder) =
            try {
                val response = client.newCall(request.build()).await()
                val alfrescoResponse = response.toAlfrescoResponse()

                when (alfrescoResponse.statusCode) {
                    Response.OK -> Result.of { alfrescoResponse }
                    else -> Result.Failure(Error(alfrescoResponse.responseMessage))
                }
            } catch (exception: Exception) {
                Result.Failure(Error(exception.message))
            }
}

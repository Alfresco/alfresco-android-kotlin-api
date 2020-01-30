package com.alfresco.core.network.contract.implementation

import com.alfresco.core.data.Result
import com.alfresco.core.data.remote.AlfrescoResponse
import com.alfresco.core.data.toAlfrescoResponse
import com.alfresco.core.network.contract.AlfrescoNetwork
import com.alfresco.core.network.contract.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

/**
 * Created by Bogdan Roatis on 3/22/2019.
 */
class OkHttpNetwork : AlfrescoNetwork {

    override suspend fun head(url: String, headers: Map<String, String>, params: Map<String, String>, data: Any?, timeout: Long): Result<AlfrescoResponse, Exception> {
        val client = getHttpClient(timeout)

        //TODO add data

        val request = Request.Builder().url(url).head()

        headers.forEach {
            request.addHeader(it.key, it.value)
        }

        return response(client, request)
    }

    override suspend fun get(url: String,
                             headers: Map<String, String>,
                             params: Map<String, String>,
                             data: Any?,
                             timeout: Long): Result<AlfrescoResponse, Exception> {

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
                              timeout: Long): Result<AlfrescoResponse, Exception> {

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

    private fun response(client: OkHttpClient, request: Request.Builder) =

//            try (client.newCall(request.build()).execute()) {
//                return response.body().string();
//            }
            try {
                val response = client.newCall(request.build()).execute()
//                val response = client.newCall(request.build()).await()
                when (response.code) {
                    Response.OK -> {
                        Result.of { response.toAlfrescoResponse() }
                    }
                    else -> Result.Error(Exception(response.body?.string()))
                }
            } catch (exception: Exception) {
                Result.Error(Exception(exception.message))
            }
}

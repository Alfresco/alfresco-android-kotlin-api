package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.data.ContentServerDetails
import com.alfresco.auth.pkce.PkceAuthService
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class DiscoveryService(val context: Context, val authConfig: AuthConfig) {

    suspend fun getAuthType(endpoint: String): AuthType {

        return when {

            isPkceType(endpoint) -> AuthType.PKCE

            isBasicType(endpoint) -> AuthType.BASIC

            else -> AuthType.UNKNOWN
        }
    }

    suspend fun isContentServicesInstalled(endpoint: String): Boolean {
        val uri = contentServicesDiscoveryEndpoint(endpoint).toString()

        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()
                val request = Request.Builder()
                    .url(URL(uri))
                    .get()
                    .build()
                val response = client.newCall(request).execute()

                if (response.code != 200) return@withContext false

                val body = response.body?.string() ?: ""
                val data = ContentServerDetails.jsonDeserialize(body)

                data?.isAtLeast(MIN_ACS_VERSION) ?: false
            } catch (e: Exception) {
                false
            }
        }
    }

    private suspend fun isBasicType(endpoint: String): Boolean = isContentServicesInstalled(endpoint)

    private suspend fun isPkceType(endpoint: String): Boolean {
        val uri = PkceAuthService.discoveryUriWith(endpoint, authConfig)
        return try {
            val authService = PkceAuthService(context, null, authConfig)
            val discoveryResult = authService.fetchDiscoveryFromUrl(uri)
            discoveryResult.isSuccess
        } catch (exception: Exception) {
            false
        }
    }

    fun serviceDocumentsEndpoint(endpoint: String): Uri {
        return PkceAuthService.endpointWith(endpoint, authConfig)
            .buildUpon()
            .appendPath(authConfig.serviceDocuments)
            .build()
    }

    private fun contentServicesDiscoveryEndpoint(endpoint: String): Uri {
        return serviceDocumentsEndpoint(endpoint)
            .buildUpon()
            .appendEncodedPath(ACS_SERVER_DETAILS)
            .build()
    }

    companion object {
        const val ACS_SERVER_DETAILS = "service/api/server"
        const val MIN_ACS_VERSION = "5.2.2"
    }
}

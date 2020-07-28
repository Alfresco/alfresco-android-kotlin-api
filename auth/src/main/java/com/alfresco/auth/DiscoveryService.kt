package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.util.concurrent.TimeUnit

class DiscoveryService(val context: Context, val authConfig: AuthConfig)
{

    suspend fun getAuthType(endpoint: String): AuthType {

        return when {

            isPkceType(endpoint) -> AuthType.PKCE

            isBasicType(endpoint) -> AuthType.BASIC

            else -> AuthType.UNKNOWN
        }
    }

    private suspend fun isBasicType(endpoint: String): Boolean {
        val uri = serviceDocumentsEndpoint(endpoint).toString()

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
                response.code == 200
            } catch (e: Exception) {
                false
            }
        }
    }

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
}

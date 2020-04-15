package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.core.data.Result
import com.alfresco.core.network.Alfresco
import com.alfresco.core.network.request.response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

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

        val result = withContext(Dispatchers.IO) {
            try {
                Alfresco.with(uri).get().response()
            } catch (e: Exception) {
                Result.Error(IllegalArgumentException())
            }
        }

        return result.isSuccess
    }

    private suspend fun isPkceType(endpoint: String): Boolean {
        val uri = PkceAuthService.discoveryUriWith(endpoint, authConfig)
        val authService = PkceAuthService(context, null, authConfig)
        return try {
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

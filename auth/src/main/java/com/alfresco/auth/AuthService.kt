package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.core.data.Result
import com.alfresco.core.extension.isBlankOrEmpty
import com.alfresco.core.network.Alfresco
import com.alfresco.core.network.request.response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState
import java.lang.IllegalArgumentException
import java.util.*


class AuthService(context: Context, authState: AuthState?, authConfig: AuthConfig)
    : PkceAuthService(context, authState, authConfig)
{

    suspend fun getAuthType(endpoint: String): AuthType {

        return when {

            isIdentityServiceType(endpoint) -> AuthType.SSO

            isBasicType(endpoint) -> AuthType.BASIC

            else -> AuthType.UNKNOWN
        }
    }

    private suspend fun isBasicType(endpoint: String): Boolean {
        val uri = serviceDocumentsEndpoint(endpoint).toString()

        val result = withContext(Dispatchers.IO) {
            try {
                Alfresco.with(uri).head().response()
            } catch (e: Exception) {
                Result.Error(IllegalArgumentException())
            }
        }

        return result.isSuccess
    }

    private suspend fun isIdentityServiceType(endpoint: String): Boolean {
        return try {
            val discoveryUri = discoveryUriWith(endpoint, authConfig)
            val discoveryResult = fetchDiscoveryFromUrl(discoveryUri)
            discoveryResult.isSuccess

        } catch (exception: Exception) {
            false
        }
    }

    fun serviceDocumentsEndpoint(endpoint: String): Uri {
        return endpointWith(endpoint, authConfig)
            .buildUpon()
            .appendPath(authConfig.serviceDocuments)
            .build()
    }
}

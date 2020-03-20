package com.alfresco.auth

import android.content.Context
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.core.extension.isBlankOrEmpty
import com.alfresco.core.network.Alfresco
import com.alfresco.core.network.request.response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState
import java.util.*


class AuthService(context: Context, authState: AuthState?, authConfig: AuthConfig)
    : PkceAuthService(context, authState, authConfig)
{

    suspend fun getAuthType(endpoint: String, authConfig: AuthConfig): AuthType {

        return when {

            isIdentityServiceType(endpoint) -> AuthType.SSO

            isBasicType(endpoint, authConfig) -> AuthType.BASIC

            else -> AuthType.UNKNOWN
        }
    }

    private suspend fun isBasicType(endpoint: String, authConfig: AuthConfig): Boolean {
        val formattedEndpoint = formatEndpoint(
            endpoint, authConfig.https, authConfig.port,
            authConfig.serviceDocuments
        ) ?: return false

        val result = withContext(Dispatchers.IO) {
            Alfresco.with(formattedEndpoint).get().response()
        }

        return result.isSuccess
    }

    private suspend fun isIdentityServiceType(endpoint: String): Boolean {
        return try {
            val generatedEndpoint = generateUri(endpoint)
            val discoveryResult = fetchDiscoveryFromUrl(generatedEndpoint)

            discoveryResult.isSuccess

        } catch (exception: Exception) {
            false
        }
    }

    fun formatEndpoint(endpoint: String?, https: Boolean, port: String?, serviceDocument: String?): String? {
        if (endpoint == null || endpoint.isBlankOrEmpty()) {
            return null
        }

        val value = endpoint.trim().toLowerCase(Locale.ROOT) +
                (if (port != null && !port.isBlankOrEmpty()) ":$port" else "")

        val builder = StringBuilder()

        // Check if starts with http ?
        if (!value.toLowerCase(Locale.ROOT).startsWith("http") && !value.startsWith("https")) {
            builder.append(if (https) "https://" else "http://")
        }

        builder.append(value)
        builder.append(if (value.endsWith("/")) "" else "/")

        if (serviceDocument != null && !serviceDocument.isBlankOrEmpty()) {
            builder.append("$serviceDocument/")
        }

        return builder.toString()
    }
}

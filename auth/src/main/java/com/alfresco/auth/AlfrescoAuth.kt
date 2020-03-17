package com.alfresco.auth

import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.core.extension.isBlankOrEmpty
import com.alfresco.core.network.Alfresco
import com.alfresco.core.network.request.response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created by Bogdan Roatis on 7/23/2019.
 */
object AlfrescoAuth {

    suspend fun getAuthType(endpoint: String, authConfig: AuthConfig): AuthType {

        return when {

            isIdentityServiceType(endpoint, authConfig) -> AuthType.SSO

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

    private suspend fun isIdentityServiceType(endpoint: String, authConfig: AuthConfig): Boolean {
        return try {
            val pkceService = PkceAuthService()

            pkceService.setGlobalAuthConfig(authConfig)
            val generatedEndpoint = pkceService.generateUri(endpoint)
            val discoveryResult = pkceService.fetchDiscoveryFromUrl(generatedEndpoint)

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

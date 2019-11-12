package com.alfresco.auth

import android.app.Activity
import android.net.Uri
import com.alfresco.auth.Config.getDefaultSamlAuthUri
import com.alfresco.auth.basic.config.BasicAuthConfig
import com.alfresco.auth.basic.data.datasource.remote.BasicAuthService
import com.alfresco.auth.basic.data.datasource.remote.mapper.BasicAuthCredentialsResponseMapper
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.auth.saml.ui.SamlLoginActivity
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

//    private const val SAML_LOGIN_GATEWAY_URL3 = "http://alfresco-identity-service.mobile.dev.alfresco.me/auth/realms/alfresco/protocol/openid-connect/auth?client_id=alfresco&redirect_uri&scope=openid&response_type=id_token%20token&nonce"
    //    const val BASIC_LOGIN_URL = "http://alfresco-identity-service.mobile.dev.alfresco.me/auth/realms/alfresco/protocol/openid-connect/token"
//    const val BASIC_LOGIN_URL = "alfresco-identity-service.mobile.dev.alfresco.me"

    fun showUI(authType: AuthType, activity: Activity, requestCode: Int, uri: Uri) {
        showUI(authType, activity, requestCode, uri.toString())
    }

    fun showUI(authType: AuthType, activity: Activity, requestCode: Int, url: String? = null) {
        when (authType) {
            AuthType.SAML -> startSamlFlow(activity, requestCode, url
                    ?: getDefaultSamlAuthUri().toString())
            else -> throw NotImplementedError()
        }
    }

    fun startSamlFlow(activity: Activity, requestCode: Int, url: String) {
        SamlLoginActivity.show(activity, url, requestCode)
    }

    fun startSamlFlow(activity: Activity, requestCode: Int, uri: Uri) {
        startSamlFlow(activity, requestCode, uri.toString())
    }

    suspend fun performBasicAuth(username: String,
                                 password: String,
                                 basicAuthConfig: BasicAuthConfig
    ) =
            BasicAuthService(BasicAuthCredentialsResponseMapper())
                    .login(username, password, basicAuthConfig)

    suspend fun getAuthType(endpoint: String, globalAuthConfig: GlobalAuthConfig): AuthType {
        return when {
            isIdentityServiceType(endpoint, globalAuthConfig) -> AuthType.SSO
            isBasicType(endpoint, globalAuthConfig) -> AuthType.BASIC
            else -> AuthType.UNKNOWN
        }
    }

    private suspend fun isBasicType(endpoint: String, globalAuthConfig: GlobalAuthConfig): Boolean {
        val formattedEndpoint = formatEndpoint(endpoint, globalAuthConfig.https) ?: return false
        val result = withContext(Dispatchers.IO) {
            Alfresco.with(formattedEndpoint).head().response()
        }

        return result.isSuccess
    }

    private suspend fun isIdentityServiceType(endpoint: String, globalAuthConfig: GlobalAuthConfig): Boolean {
        return try {
            val pkceService = PkceAuthService()
            pkceService.setGlobalAuthConfig(globalAuthConfig)
            val generatedEndpoint = pkceService.generateUri(endpoint, globalAuthConfig.realm)
            val discoveryResult = pkceService.fetchDiscoveryFromUrl(generatedEndpoint)
            discoveryResult.isSuccess
        } catch (exception: Exception) {
            false
        }
    }

    public fun formatEndpoint(endpoint: String?, https: Boolean): String? {
        if (endpoint == null || endpoint.isBlankOrEmpty()) {
            return null
        }

        val value = endpoint.trim().toLowerCase(Locale.ROOT)

        val builder = StringBuilder()

        // Check if starts with http ?
        if (value.toLowerCase(Locale.ROOT).startsWith("http") || value.startsWith("https")) {
            // Do Nothing. We consider it's a plain url
            builder.append(value)
            builder.append(if (value.endsWith("/")) "" else "/")
        } else {
            builder.append(if (https) "https://" else "http://")
            builder.append(value)
            builder.append(if (value.endsWith("/")) "activiti-app/" else "/activiti-app/")
        }

        return builder.toString()
    }
}

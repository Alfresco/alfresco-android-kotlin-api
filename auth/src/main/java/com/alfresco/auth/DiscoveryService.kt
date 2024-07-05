package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.data.AppConfigDetails
import com.alfresco.auth.data.ContentServerDetails
import com.alfresco.auth.data.ContentServerDetailsData
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Class that facilitates service discovery process.
 */
class DiscoveryService(
    private val context: Context,
    private val authConfig: AuthConfig
) {

    /**
     * Determine which [AuthType] is supported by the [endpoint].
     */
    suspend fun getAuthType(endpoint: String): AuthType {

        when (authConfig.authType.lowercase()) {
            AuthType.OIDC.value -> {
                if (isOIDC(endpoint)){
                    return AuthType.OIDC
                }
            }
            AuthType.PKCE.value -> {
                if (isPkceType(endpoint)){
                    return AuthType.PKCE
                }
            }
        }

        return when {

            isPkceType(endpoint) -> AuthType.PKCE

            isOIDC(endpoint) -> AuthType.OIDC

            isBasicType(endpoint) -> AuthType.BASIC

            else -> AuthType.UNKNOWN
        }
    }

    /**
     * Check whether the content service is running on [endpoint].
     */
    suspend fun isContentServiceInstalled(endpoint: String): Boolean {
        val uri = contentServiceDiscoveryUrl(endpoint).toString()

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

    suspend fun isOIDCInstalled(appConfigURL: String): Boolean {
        val uri = PkceAuthService.discoveryUriWithAuth0(appConfigURL).toString()
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
                val data = AppConfigDetails.jsonDeserialize(body)
                return@withContext data?.oauth2?.audience?.isNotBlank() == true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    /**
     * returns content server details based on [endpoint].
     */
    suspend fun getContentServiceDetails(endpoint: String): ContentServerDetailsData? {
        val uri = contentServiceDiscoveryUrl(endpoint).toString()

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

                if (response.code != 200) return@withContext null

                val body = response.body?.string() ?: ""
                val data = ContentServerDetails.jsonDeserialize(body)
                data?.data
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun isBasicType(endpoint: String): Boolean = isContentServiceInstalled(endpoint)

    private suspend fun isPkceType(endpoint: String): Boolean {
        val uri = PkceAuthService.discoveryUriWith(endpoint, authConfig)
        val result = try {
            val authService = PkceAuthService(context, null, authConfig)
            authService.fetchDiscoveryFromUrl(uri)
        } catch (exception: Exception) {
            null
        }
        return result != null
    }

    private suspend fun isOIDC(endpoint: String): Boolean = isOIDCInstalled(endpoint) && authConfig.realm.isBlank()

    /**
     * Return content service url based on [endpoint].
     */
    fun contentServiceUrl(endpoint: String): Uri =
        PkceAuthService.endpointWith(endpoint, authConfig)
            .buildUpon()
            .appendPath(authConfig.contentServicePath)
            .build()

    fun oidcUrl(endpoint: String): Uri =
        PkceAuthService.endpointWith(endpoint, authConfig)
            .buildUpon()
            .appendPath("alfresco")
            .build()

    private fun contentServiceDiscoveryUrl(endpoint: String): Uri =
        contentServiceUrl(endpoint)
            .buildUpon()
            .appendEncodedPath(ACS_SERVER_DETAILS)
            .build()

    private companion object {
        const val ACS_SERVER_DETAILS = "service/api/server"
        const val MIN_ACS_VERSION = "5.2.2"
        const val ENTERPRISE = "Enterprise"
    }
}

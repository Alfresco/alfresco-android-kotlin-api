package com.alfresco.auth

import android.content.Context
import android.net.Uri
import com.alfresco.auth.data.AppConfigDetails
import com.alfresco.auth.data.ContentServerDetails
import com.alfresco.auth.data.ContentServerDetailsData
import com.alfresco.auth.data.MobileSettings
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
    private var authConfig: AuthConfig
) {

    fun setAuthConfig(msData: MobileSettings?) {
        authConfig =
            AuthConfig(
                https = msData?.https == true,
                port = msData?.port?.toString() ?: "",
                host = msData?.host?.toString() ?: "",
                contentServicePath = msData?.contentServicePath ?: "",
                realm = msData?.realm ?: "",
                clientId = msData?.android?.clientId ?: "",
                redirectUrl = msData?.android?.clientId ?: "",
                scope = msData?.scope ?: "",
            )
    }

    /**
     * Determine which [AuthType] is supported by the [endpoint].
     */
    suspend fun getAuthType(endpoint: String, host: String?): AuthType {
        return when {

            isPkceType() -> AuthType.PKCE

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
                data?.isAtLeast(MIN_ACS_VERSION) == true
            } catch (e: Exception) {
                false
            }
        }
    }

    /**
     * Check whether the content service is running on [endpoint].
     */
    internal suspend fun getAppConfigOAuthType(endpoint: String): AppConfigDetails? {
        val uri = "https://$endpoint/app-config.json"

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
                val data = AppConfigDetails.jsonDeserialize(body)
                data
            } catch (e: Exception) {
                null
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
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun isBasicType(endpoint: String): Boolean = isContentServiceInstalled(endpoint)

    private suspend fun isPkceType(): Boolean {
        val uri = PkceAuthService.discoveryUriWith(authConfig)
        val result = try {
            if (uri != null) {
                val authService = PkceAuthService(context, null, authConfig)
                authService.fetchDiscoveryFromUrl(uri)
            } else {
                null
            }
        } catch (exception: Exception) {
            exception.printStackTrace();
            null
        }
        return result != null
    }

    /**
     * Return content service url based on [endpoint].
     */
    fun contentServiceUrl(endpoint: String): Uri =
        PkceAuthService.endpointWith(endpoint, authConfig)
            .buildUpon()
            .apply {
                if (authConfig.contentServicePath.isNotEmpty()) {
                    appendPath(authConfig.contentServicePath)
                } else {
                    appendPath("alfresco")
                }
            }
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

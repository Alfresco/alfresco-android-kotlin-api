package com.alfresco.auth.pkce

import android.net.Uri
import net.openid.appauth.connectivity.ConnectionBuilder
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Creates {@link java.net.HttpURLConnection} instances using the default, platform-provided
 * mechanism, with sensible production defaults.
 */
class PkceConnectionBuilder private constructor()// no need to construct new instances
    : ConnectionBuilder {

    @Throws(IOException::class)
    override fun openConnection(uri: Uri): HttpURLConnection {
        check(HTTP_SCHEME == uri.scheme || HTTPS_SCHEME == uri.scheme) { "scheme or uri must be http or https" }

        return with(URL(uri.toString()).openConnection() as HttpURLConnection) {
            connectTimeout = CONNECTION_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            instanceFollowRedirects = false

            this
        }
    }

    companion object {
        val INSTANCE = PkceConnectionBuilder()

        private val CONNECTION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(15).toInt()
        private val READ_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10).toInt()

        private const val HTTP_SCHEME = "http"
        private const val HTTPS_SCHEME = "https"
    }
}
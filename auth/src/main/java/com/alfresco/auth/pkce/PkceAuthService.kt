package com.alfresco.auth.pkce

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.alfresco.auth.AuthConfig
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse
import net.openid.appauth.browser.AnyBrowserMatcher
import net.openid.appauth.connectivity.ConnectionBuilder
import java.util.Locale
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.resumeWithException

internal class PkceAuthService(context: Context, authState: AuthState?, authConfig: AuthConfig) {

    private val connectionBuilder: ConnectionBuilder
    private val authService: AuthorizationService
    private val authConfig: AuthConfig
    private var authState: AtomicReference<AuthState>

    init {
        checkConfig(authConfig)
        this.authState = AtomicReference()
        this.authState.set(authState)
        this.authConfig = authConfig

        this.connectionBuilder = getConnectionBuilder()
        authService = AuthorizationService(
            context,
            AppAuthConfiguration.Builder()
                .setBrowserMatcher(AnyBrowserMatcher.INSTANCE)
                .setConnectionBuilder(connectionBuilder)
                .setSkipIssuerHttpsCheck(!authConfig.https)
                .build()
        )
    }

    /**
     * Fetch an [AuthorizationServiceConfiguration] from an [openIdConnectIssuerUri].
     * This method automatically appends the OpenID connect well-known configuration path to the
     * url.
     * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html">OpenID Connect discovery 1.0</a>
     */
    suspend fun fetchDiscoveryFromUrl(openIdConnectIssuerUri: Uri) =
        suspendCancellableCoroutine<AuthorizationServiceConfiguration> {
            AuthorizationServiceConfiguration.fetchFromUrl(
                openIdConnectIssuerUri,
                { serviceConfiguration, ex ->
                    when {
                        serviceConfiguration != null -> {
                            it.resumeWith(Result.success(serviceConfiguration))
                        }

                        ex != null -> {
                            it.resumeWithException(ex)
                        }

                        else -> it.resumeWithException(Exception())
                    }
                },
                connectionBuilder
            )
        }

    /**
     * Initiates the login in [activity] with activity result [requestCode]
     */
    suspend fun initiateLogin(endpoint: String, launcher: ActivityResultLauncher<Intent>) {
        require(endpoint.isNotBlank()) { "Identity url is blank or empty" }
        checkConfig(authConfig)

        val discoveryUri: Uri = discoveryUriWith(authConfig)

        withContext(Dispatchers.IO) {
            val config = fetchDiscoveryFromUrl(discoveryUri)

            // save the authorization configuration
            authState.set(AuthState(config))

            val authRequest = generateAuthorizationRequest(config)
            val authIntent = generateAuthIntent(authRequest)

            withContext(Dispatchers.Main) {
                launcher.launch(authIntent)
            }
        }
    }

    fun initiateReLogin(launcher: ActivityResultLauncher<Intent>) {
        requireNotNull(authState.get())

        val authRequest = generateAuthorizationRequest(authState.get().authorizationServiceConfiguration!!)
        val authIntent = generateAuthIntent(authRequest)

        launcher.launch(authIntent)
    }

    /**
     * Generate an intent to start the authentication flow
     *
     * @param authRequest
     * @return the [Intent] used to start the authentication flow
     */
    private fun generateAuthIntent(authRequest: AuthorizationRequest): Intent {
        return authService.getAuthorizationRequestIntent(authRequest)
    }

    /**
     *
     * @param intent the intent received as a result from the initiation of the pkce login action
     */
    suspend fun getAuthResponse(intent: Intent): String {
        val authResponse = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)

        authState.get()?.update(authResponse, exception)

        if (authResponse != null) {
            return getToken(authResponse)
        }

        throw exception ?: Exception()
    }

    /**
     * Sends a request to the authorization service to exchange a code granted as part of an
     * authorization request for a token.
     *
     * @param authorizationResponse
     */
    private suspend fun getToken(authorizationResponse: AuthorizationResponse) =
        withContext(Dispatchers.IO) {

            var clientAuth = if (authConfig.secret.isNotEmpty()) {
                ClientSecretPost(authConfig.secret)
            } else {
                authState.get().clientAuthentication
            }

            suspendCancellableCoroutine<String> {
                authService.performTokenRequest(
                    authorizationResponse.createTokenExchangeRequest(),
                    clientAuth,
                ) { response: TokenResponse?, ex: AuthorizationException? ->
                    authState.get().update(response, ex)

                    when {
                        response != null -> {
                            it.resumeWith(Result.success(authState.get().jsonSerializeString()))
                        }

                        ex != null -> {
                            it.resumeWithException(ex)
                        }

                        else -> it.resumeWithException(Exception())
                    }
                }
            }
        }

    suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<TokenResponse> {
                authService.performTokenRequest(
                    authState.get().createTokenRefreshRequest(),
                    authState.get().clientAuthentication
                ) { response: TokenResponse?, ex: AuthorizationException? ->

                    authState.get().update(response, ex)

                    when {
                        response != null -> {
                            it.resumeWith(Result.success(response))
                        }

                        ex != null -> {
                            it.resumeWithException(ex)
                        }

                        else -> it.resumeWithException(Exception())
                    }
                }
            }
        }

    suspend fun endSession(launcher: ActivityResultLauncher<Intent>) {
        withContext(Dispatchers.IO) {
            val request = makeEndSessionRequest(authState.get().authorizationServiceConfiguration!!)
            val intent = authService.getEndSessionRequestIntent(request)
            withContext(Dispatchers.Main) {
                launcher.launch(intent)
            }
        }
    }

    fun getUserEmail(): String? {
        try {

            val idToken = authState.get().idToken
            if (idToken != null) {
                val decodedToken = JWT(idToken)

                return decodedToken.getClaim("email").asString()
            }

            return null
        } catch (ex: java.lang.Exception) {
            return null
        }
    }

    fun getAuthState(): AuthState? {
        return authState.get()
    }

    /**
     * Generates [AuthorizationRequest] used for creating the auth flow intent
     */
    private fun generateAuthorizationRequest(serviceAuthorization: AuthorizationServiceConfiguration):
            AuthorizationRequest {

        val builder = AuthorizationRequest.Builder(
            serviceAuthorization,
            authConfig.clientId,
            ResponseTypeValues.CODE,
            Uri.parse(authConfig.redirectUrl)
        )

        if (authConfig.scope.isNotEmpty()) {

            builder.setScope(authConfig.scope)
        }

        if (authConfig.additionalParams.isNotEmpty()) {
            builder.setAdditionalParameters(authConfig.additionalParams)
        }

        return builder.build()
    }

    private fun makeEndSessionRequest(serviceAuthorization: AuthorizationServiceConfiguration): EndSessionRequest {
        return EndSessionRequest.Builder(serviceAuthorization)
            .setIdTokenHint(authState.get().idToken!!)
            .setPostLogoutRedirectUri(Uri.parse(authConfig.redirectUrl))
            .build()
    }

    private fun getConnectionBuilder(): PkceConnectionBuilder {
        return PkceConnectionBuilder.INSTANCE
    }

    /**
     * Checks if [AuthConfig] has all the necessary information
     * @throws [IllegalArgumentException]
     */
    private fun checkConfig(authConfig: AuthConfig) {
        require(authConfig.clientId.isNotBlank()) { "Client id is blank or empty" }
    }

    companion object {

        /**
         * The standard base path for auth
         */
        private const val AUTH = "auth"

        /**
         * The standard base path for realms
         */
        private const val REALMS = "realms"

        /**
         * The standard base path for well-known resources on domains.
         *
         * @see "Defining Well-Known Uniform Resource Identifiers
         */
        private const val WELL_KNOWN_PATH = ".well-known"

        /**
         * The standard resource under [.well-known][.WELL_KNOWN_PATH] at which an OpenID Connect
         * discovery document can be found under an issuer's base URI.
         *
         * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html">OpenID Connect discovery 1.0</a>
         */
        private const val OPENID_CONFIGURATION_RESOURCE = "openid-configuration"

        /**
         * Creates an [endpoint] uri using [config].
         * If the [endpoint] contains either schema or port that will override [config] information.
         */
        fun endpointWith(endpoint: String, config: AuthConfig): Uri {
            val src = endpoint.trim().lowercase(Locale.ROOT)

            var uri = Uri.parse(src)
            var uriBuilder = uri.buildUpon()

            // e.g. hostname:port is not hierarchical
            if (!uri.isHierarchical || uri.isRelative || uri.authority == null) {
                uriBuilder = Uri.Builder().encodedAuthority(src)
                uri = uriBuilder.build()
            }

            if (uri.scheme == null || (uri.scheme != "http" && uri.scheme != "https")) {
                uriBuilder = uriBuilder.scheme(if (config.https) "https" else "http")
            }

            if (uri.port == -1 && ((config.https && config.port != "443") || (!config.https && config.port != "80"))) {
                val port = config.port.takeIf { it.isEmpty() } ?: ":${config.port}"
                uriBuilder = uriBuilder.encodedAuthority(uri.authority + port)
            }

            return uriBuilder.build()
        }

        fun discoveryUriWith(config: AuthConfig): Uri {

            return Uri.parse(config.host)
                .buildUpon()
                .apply {
                    if (config.realm.isNotEmpty()) {
                        appendPath(AUTH)
                        appendPath(REALMS)
                        appendPath(config.realm)
                    }
                }
                .appendPath(WELL_KNOWN_PATH)
                .appendPath(OPENID_CONFIGURATION_RESOURCE)
                .build()
        }


        fun endpointWithHost(endpoint: String, config: AuthConfig): Uri {
            val src = endpoint.trim().lowercase(Locale.ROOT)

            var uri = Uri.parse(src)
            var uriBuilder = uri.buildUpon()

            if (config.realm.isNotEmpty() && config.contentServicePath.isNotEmpty()) {
                uriBuilder.appendPath(AUTH)
                    .appendPath(REALMS)
                    .appendPath(config.realm)
            }

            return uriBuilder.build()
        }
    }
}

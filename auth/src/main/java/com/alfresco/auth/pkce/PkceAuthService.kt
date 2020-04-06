package com.alfresco.auth.pkce

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alfresco.auth.AuthConfig
import com.alfresco.core.data.Result
import com.alfresco.core.extension.isNotBlankNorEmpty
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import net.openid.appauth.*
import net.openid.appauth.browser.AnyBrowserMatcher
import net.openid.appauth.connectivity.ConnectionBuilder
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.resumeWithException

open class PkceAuthService(context: Context, authState: AuthState?, authConfig: AuthConfig) {

    protected val context: Context
    protected val authConfig: AuthConfig

    private val connectionBuilder: ConnectionBuilder
    private val authService: AuthorizationService
    protected var authState: AtomicReference<AuthState>

    init {
        checkConfig(authConfig)
        this.authState = AtomicReference()
        this.authState.set(authState)
        this.authConfig = authConfig
        this.context = context

        this.connectionBuilder = getConnectionBuilder()
        authService = AuthorizationService(
            context,
            AppAuthConfiguration.Builder()
                .setBrowserMatcher(AnyBrowserMatcher.INSTANCE)
                .setConnectionBuilder(connectionBuilder)
                .build()
        )
    }

    /**
     * Fetch an AuthorizationServiceConfiguration from an OpenID Connect issuer url.
     * This method automatically appends the OpenID connect well-known configuration path to the
     * url.
     *
     * @param openIdConnectIssuerUrl The issuer url, e.g. "https://accounts.google.com"
     * @see <a href="https://openid.net/specs/openid-connect-discovery-1_0.html">OpenID Connect discovery 1.0</a>
     */
    suspend fun fetchDiscoveryFromUrl(openIdConnectIssuerUri: Uri) =
        suspendCancellableCoroutine<Result<AuthorizationServiceConfiguration, AuthorizationException>> {
            AuthorizationServiceConfiguration.fetchFromUrl(
                openIdConnectIssuerUri,
                AuthorizationServiceConfiguration.RetrieveConfigurationCallback { serviceConfiguration, ex ->
                    when {
                        ex != null -> {
                            it.resumeWith(kotlin.Result.success(Result.error(ex)))
                        }
                        serviceConfiguration != null -> {
                            it.resumeWith(kotlin.Result.success(Result.success(serviceConfiguration)))
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
    suspend fun initiateLogin(endpoint: String, activity: Activity, requestCode: Int) {
        require(endpoint.isNotBlankNorEmpty()) { "Identity url is blank or empty" }

        // build discovery url using auth configuration
        val discoveryUri = discoveryUriWith(endpoint, authConfig)

        withContext(Dispatchers.IO) {
            with(fetchDiscoveryFromUrl(discoveryUri)) {


                onSuccess {
                    // save the authorization configuration
                    authState.set(AuthState(it))

                    val authRequest = generateAuthorizationRequest(it)
                    val authIntent = generateAuthIntent(authRequest)

                    withContext(Dispatchers.Main) {
                        activity.startActivityForResult(authIntent, requestCode)
                    }
                }
                onError { throw it }
            }
        }
    }

    suspend fun initiateReLogin(activity: Activity, requestCode: Int) {
        requireNotNull(authState.get())

        withContext(Dispatchers.IO) {
            val authRequest = generateAuthorizationRequest(authState.get().authorizationServiceConfiguration!!)
            val authIntent = generateAuthIntent(authRequest)
            withContext(Dispatchers.Main) {
                activity.startActivityForResult(authIntent, requestCode)
            }
        }
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
    suspend fun getAuthResponse(intent: Intent): Result<String, AuthorizationException> {
        val authResponse = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)

        authState.get()?.update(authResponse, exception)

        if (authResponse != null) {
            return getToken(authResponse)
        } else if (exception != null) {
            return Result.error(exception)
        }

        throw Exception()
    }

    /**
     * Sends a request to the authorization service to exchange a code granted as part of an
     * authorization request for a token.
     *
     * @param authorizationResponse
     */
    private suspend fun getToken(authorizationResponse: AuthorizationResponse) =
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<String, AuthorizationException>> {
                authService.performTokenRequest(
                    authorizationResponse.createTokenExchangeRequest(),
                    authState.get().clientAuthentication
                ) { response: TokenResponse?, ex: AuthorizationException? ->
                    authState.get().update(response, ex)

                    when {
                        response != null -> {
                            it.resumeWith(kotlin.Result.success(Result.success(authState.get().jsonSerializeString())))
                        }
                        ex != null -> {
                            it.resumeWith(kotlin.Result.success(Result.error(ex)))
                        }
                        else -> it.resumeWithException(Exception())
                    }
                }
            }
        }

    suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<TokenResponse, AuthorizationException>> {
                authService.performTokenRequest(
                    authState.get().createTokenRefreshRequest(),
                    authState.get().clientAuthentication
                ) { response: TokenResponse?, ex: AuthorizationException? ->

                    authState.get().update(response, ex)

                    when {
                        response != null -> {
                            it.resumeWith(kotlin.Result.success(Result.success(response)))
                        }
                        ex != null -> {
                            it.resumeWith(kotlin.Result.success(Result.error(ex)))
                        }
                        else -> it.resumeWithException(Exception())
                    }
                }
            }
        }

    suspend fun endSession(activity: Activity, requestCode: Int) {
        withContext(Dispatchers.IO) {
            val request = makeEndSessionRequest(authState.get().authorizationServiceConfiguration!!)
            val intent = authService.getEndSessionRequestIntent(request)
            withContext(Dispatchers.Main) {
                activity.startActivityForResult(intent, requestCode)
            }
        }
    }

    fun getUserEmail() : String? {
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

    fun getAuthState() : AuthState? {
        return authState.get()
    }

    /**
     * Generates [AuthorizationRequest] used for creating the auth flow intent
     */
    private fun generateAuthorizationRequest(serviceAuthorization: AuthorizationServiceConfiguration):
            AuthorizationRequest {

        var builder = AuthorizationRequest.Builder(
            serviceAuthorization,
            authConfig.clientId,
            ResponseTypeValues.CODE,
            Uri.parse(authConfig.redirectUrl)
        )

        builder.setScope("openid")

        val authRequest = builder.build()

        return authRequest
    }

    private fun makeEndSessionRequest(serviceAuthorization: AuthorizationServiceConfiguration): EndSessionRequest {
        return EndSessionRequest.Builder(
            serviceAuthorization,
            authState.get().idToken!!,
            Uri.parse(authConfig.redirectUrl))
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
        requireNotNull(authConfig.port.toIntOrNull()) { "Invalid port or empty" }
        require(authConfig.serviceDocuments.isNotBlankNorEmpty()) { "Service documents is blank or empty" }
        require(authConfig.realm.isNotBlankNorEmpty()) { "Realm is blank or empty" }
        require(authConfig.clientId.isNotBlankNorEmpty()) { "Client id is blank or empty" }
        require(authConfig.redirectUrl.isNotBlankNorEmpty()) { "Redirect url is blank or empty" }
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
            val src = endpoint.trim().toLowerCase(Locale.ROOT)

            var uri = Uri.parse(src)
            var uriBuilder = uri.buildUpon()

            // e.g. hostname:port is not hierarchical
            if (!uri.isHierarchical) {
                uriBuilder = Uri.Builder().encodedAuthority(src)
                uri = uriBuilder.build()
            }

            if (uri.scheme == null || (uri.scheme != "http" && uri.scheme != "https")) {
                uriBuilder = uriBuilder.scheme(if (config.https) "https" else "http")
            }

            if (uri.port == -1 && ((config.https && config.port != "443") || (!config.https && config.port != "80"))) {
                uriBuilder = uriBuilder.authority(uri.authority + ":${config.port}")
            }

            return uriBuilder.build()
        }

        fun discoveryUriWith(endpoint: String, config: AuthConfig): Uri {
            return endpointWith(endpoint, config)
                .buildUpon()
                .appendPath(AUTH)
                .appendPath(REALMS)
                .appendPath(config.realm)
                .appendPath(WELL_KNOWN_PATH)
                .appendPath(OPENID_CONFIGURATION_RESOURCE)
                .build()
        }
    }
}

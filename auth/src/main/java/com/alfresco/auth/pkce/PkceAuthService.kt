package com.alfresco.auth.pkce

import android.app.Activity
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
import net.openid.appauth.connectivity.DefaultConnectionBuilder
import java.util.*
import kotlin.coroutines.resumeWithException

/**
 * Created by Bogdan Roatis on 10/3/2019.
 */
class PkceAuthService {

    private lateinit var authService: AuthorizationService
    private lateinit var authStateManager: AuthStateManager
    private lateinit var connectionBuilder: ConnectionBuilder
    private lateinit var authConfig: AuthConfig

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
    }

    fun setGlobalAuthConfig(authConfig: AuthConfig) {
        checkConfig(authConfig)

        this.authConfig = authConfig

        // init the connection builder
        connectionBuilder = getConnectionBuilder(authConfig.https)
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
     * Initiates the login
     *
     * @param activity
     * @param requestCode
     * @param pkceAuthConfig
     */
    suspend fun initiateLogin(endpoint: String, activity: Activity, requestCode: Int) {
        // check if the config has all the necessary info
        checkConfig(authConfig)

        require(endpoint.isNotBlankNorEmpty()) { "Discovery url is blank or empty" }

        authStateManager = AuthStateManager.getInstance(activity)

        // generate the url from the auth configuration
        val generatedUri = generateUri(endpoint)

        withContext(Dispatchers.IO) {
            with(fetchDiscoveryFromUrl(generatedUri)) {


                onSuccess {

                    // save the authorization configuration
                    authStateManager.replace(AuthState(it))

                    val authRequest = generateAuthorizationRequest(it)
                    val authIntent = generateAuthIntent(activity, authRequest)

                    withContext(Dispatchers.Main) {
                        activity.startActivityForResult(authIntent, requestCode)
                    }
                }
                onError { throw it }
            }
        }
    }

    fun generateUri(issuerUrl: String) : Uri {
        val builder = StringBuilder()
        val value = issuerUrl.trim().toLowerCase(Locale.ROOT) + ":${authConfig.port}"

        if (!value.toLowerCase(Locale.ROOT).startsWith("http") && !value.startsWith("https")) {
            builder.append(if (authConfig.https) "https://" else "http://")
        }

        builder.append(value)

        return Uri.parse(builder.toString()).buildUpon()
            .appendPath(AUTH)
            .appendPath(REALMS)
            .appendPath(authConfig.realm)
            .appendPath(WELL_KNOWN_PATH)
            .appendPath(OPENID_CONFIGURATION_RESOURCE)
            .build()
    }

    /**
     * Generate an intent to start the authentication flow
     *
     * @param activity
     * @param authRequest
     * @return the [Intent] used to start the authentication flow
     */
    private fun generateAuthIntent(activity: Activity, authRequest: AuthorizationRequest): Intent {
        authService = AuthorizationService(
            activity,
            AppAuthConfiguration.Builder()
                .setBrowserMatcher(AnyBrowserMatcher.INSTANCE)
                .setConnectionBuilder(connectionBuilder)
                .build()
        )

        return authService.getAuthorizationRequestIntent(authRequest)
    }

    /**
     *
     * @param intent the intent received as a result from the initiation of the pkce login action
     */
    suspend fun getAuthResponse(intent: Intent): Result<TokenResponse, AuthorizationException> {
        val authResponse = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)

        authStateManager.updateAfterAuthorization(authResponse, exception)

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
            suspendCancellableCoroutine<Result<TokenResponse, AuthorizationException>> {
                authService.performTokenRequest(
                    authorizationResponse.createTokenExchangeRequest(),
                    authStateManager.current.clientAuthentication
                ) { response: TokenResponse?, ex: AuthorizationException? ->
                    authStateManager.updateAfterTokenResponse(response, ex)

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

    suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<TokenResponse, AuthorizationException>> {
                authService.performTokenRequest(
                    authStateManager.current.createTokenRefreshRequest(),
                    authStateManager.current.clientAuthentication
                ) { response: TokenResponse?, ex: AuthorizationException? ->

                    authStateManager.updateAfterTokenResponse(response, ex)

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

    fun signOut() {
        // discard the authorization and token state, but retain the configuration and
        // dynamic client registration (if applicable), to save from retrieving them again.
        val currentState = authStateManager.current
        val clearedState = AuthState(currentState.authorizationServiceConfiguration!!)
        if (currentState.lastRegistrationResponse != null) {
            clearedState.update(currentState.lastRegistrationResponse)
        }
        authStateManager.replace(clearedState)
    }

    fun getUserEmail() : String? {
        val authState = authStateManager.current

        try {

            val idToken = authState.idToken
            if(idToken != null) {
                val decodedToken = JWT(idToken)

                return decodedToken.getClaim("email").asString()
            }

            return null

        } catch (ex: java.lang.Exception) {
            return null
        }
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

    /**
     * @return a [ConnectionBuilder] based on the https flag. If https is required
     * [DefaultConnectionBuilder] will be returned
     * otherwise a [ConnectionBuilderForTesting] will be returned
     */
    private fun getConnectionBuilder(https: Boolean): ConnectionBuilder {
        return if (https) DefaultConnectionBuilder.INSTANCE
        else ConnectionBuilderForTesting.INSTANCE
    }

    /**
     * Checks if [AuthConfig] has all the necessary information
     */
    private fun checkConfig(authConfig: AuthConfig) {
        require(authConfig.clientId.isNotBlankNorEmpty()) { "Client id is blank or empty" }
        require(authConfig.redirectUrl.isNotBlankNorEmpty()) { "Redirect url is blank or empty" }
    }
}
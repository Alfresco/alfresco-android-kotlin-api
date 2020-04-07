package com.alfresco.auth

import android.content.Context
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.*
import net.openid.appauth.AuthState
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException

class AuthInterceptor(private val context: Context, private val stateString: String, private val config: AuthConfig) : Interceptor {

    private val localScope = CoroutineScope(Dispatchers.IO)

    private var listener: Listener? = null
    private val pkceAuthService: PkceAuthService
    private var lastRefresh = 0L

    private var scheduledRefreshJob: Job? = null

    init {
        val state = try { AuthState.jsonDeserialize(stateString) } catch (ex: JSONException) { null }
        pkceAuthService = PkceAuthService(context, state, config)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var state = pkceAuthService.getAuthState() ?: return chain.proceed(chain.request())

        // Preemptive token refresh when close to expiration
        refreshTokenIfNeeded(state)?.let { state = it  }

        var response = chain.proceed(state.accessToken)

        // When unauthorized try to refresh
        if (response.code == HTTP_RESPONSE_401_UNAUTHORIZED) {
            val newState = refreshTokenNow()

            if (newState != null) {
                response.close()
                response = chain.proceed(newState.accessToken)
            }
        }

        // If still error notify listener of failure
        if (response.code == HTTP_RESPONSE_401_UNAUTHORIZED) {
            listener?.onAuthFailure()
        }

        return response
    }

    @Synchronized private fun refreshTokenNow(): AuthState? {
        val state = pkceAuthService.getAuthState() ?: return null

        // Another thread might've refreshed the token already
        val current = System.currentTimeMillis()
        if (current - lastRefresh < REFRESH_THROTTLE_DELAY) {
            return state
        }

        cancelScheduledTokenRefresh()
        lastRefresh = current

        val result = runBlocking { runTokenRefresh() }

        if (result != null) {
            scheduleTokenRefresh(result)
        }

        return result
    }

    private fun refreshTokenIfNeeded(state: AuthState): AuthState? {
        val expiration = state.accessTokenExpirationTime ?: return null
        val delta = expiration - System.currentTimeMillis()

        if (delta < REFRESH_DELTA_BEFORE_EXPIRY) {
            return refreshTokenNow()
        } else {
            scheduleTokenRefresh(state)
        }

        return null
    }

    @Synchronized fun scheduleTokenRefresh(state: AuthState) {
        val expiration = state.accessTokenExpirationTime ?: return

        val delta = expiration - System.currentTimeMillis() - REFRESH_DELTA_BEFORE_EXPIRY
        if (delta < 0) return

        cancelScheduledTokenRefresh()
        scheduledRefreshJob = localScope.launch {
            delay(delta)
            refreshTokenNow()
        }
    }

    private fun cancelScheduledTokenRefresh() {
        scheduledRefreshJob?.cancel()
        scheduledRefreshJob = null
    }

    private suspend fun runTokenRefresh(): AuthState? {
        val result = pkceAuthService.refreshToken()

        if (result.isSuccess) {
            val state = pkceAuthService.getAuthState()
            state?.jsonSerializeString()?.let {
                listener?.onAuthStateChange(it)
            }
            return state
        }

        return null
    }

    private fun Interceptor.Chain.proceed(token: String?): Response {
        val request = if (token != null) request().newBuilder().addToken(token).build() else request()
        return proceed(request)
    }

    private fun Request.Builder.addToken(token: String) =
        this.apply { removeHeader("Authorization") }
            .apply { header("Authorization", "Bearer $token") }

    companion object {
        private const val HTTP_RESPONSE_401_UNAUTHORIZED = 401
        private const val REFRESH_THROTTLE_DELAY = 15000L
        private const val REFRESH_DELTA_BEFORE_EXPIRY = 20000L
    }

    interface Listener {
        fun onAuthStateChange(authState: String)
        fun onAuthFailure()
    }
}

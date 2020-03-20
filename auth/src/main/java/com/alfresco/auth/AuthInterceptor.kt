package com.alfresco.auth

import android.content.Context
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import java.util.concurrent.atomic.AtomicReference

class AuthInterceptor(private val context: Context, private val stateString: String, private val config: AuthConfig) : Interceptor {

    private var changeListener: ChangeListener? = null
    private var pkceAuthService: PkceAuthService
    private var lastRefresh = 0L

    init {
        val state = try { AuthState.jsonDeserialize(stateString) } catch (ex: JSONException) { null }
        pkceAuthService = PkceAuthService(context, state, config)
    }

    fun setChangeListener(listener: ChangeListener) {
        this.changeListener = listener
    }

    // TODO: Thread Safe
    override fun intercept(chain: Interceptor.Chain): Response {
        val state = pkceAuthService.getAuthState() ?: return chain.proceed(chain.request())

        val response = chain.proceed(state.accessToken)

        // TODO: how about basic auth?
        if (response.code == 401) {
            val newState = refreshToken(state)

            if (newState != null) {
                response.close()
                return chain.proceed(newState.accessToken)
            }
        }

        return response
    }

    @Synchronized private fun refreshToken(state: AuthState?): AuthState? {
        val current = System.currentTimeMillis()
        if (current - lastRefresh < REFRESH_TOKEN_DELAY) {
            return state
        }

        lastRefresh = current
        var newState: AuthState? = null

        runBlocking {
            with(pkceAuthService.refreshToken()) {
                onSuccess {
                    newState = pkceAuthService.getAuthState()
                    newState?.jsonSerializeString()?.let { state ->
                        changeListener?.onChange(state)
                    }
                }
                onError {
                    // do nothing
                }
            }
        }

        return newState
    }

    private fun Interceptor.Chain.proceed(token: String?): Response {
        val request = if (token != null) request().newBuilder().addToken(token).build() else request()
        return proceed(request)
    }

    private fun Request.Builder.addToken(token: String) =
        this.apply { removeHeader("Authorization") }
            .apply { header("Authorization", "Bearer $token") }

    companion object {
        private const val REFRESH_TOKEN_DELAY = 5000L
    }

    interface ChangeListener {
        fun onChange(authState: String)
    }
}

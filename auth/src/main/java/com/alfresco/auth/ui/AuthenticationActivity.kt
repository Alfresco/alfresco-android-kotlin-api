package com.alfresco.auth.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthType
import com.alfresco.auth.Credentials
import com.alfresco.auth.DiscoveryService
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.auth.data.LiveEvent
import com.alfresco.auth.data.MutableLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState

abstract class AuthenticationViewModel : ViewModel() {

    protected abstract var context: Context
    protected lateinit var discoveryService: DiscoveryService

    protected val _onCredentials = MutableLiveEvent<Credentials>()
    protected val _onError = MutableLiveEvent<String>()

    val onCredentials: LiveEvent<Credentials> get() = _onCredentials
    val onError: LiveEvent<String> get() = _onError

    var isReLogin = false

    fun checkAuthType(endpoint: String, authConfig: AuthConfig) {
        discoveryService = DiscoveryService(context, authConfig)

        viewModelScope.launch {
            val authType = discoveryService.getAuthType(endpoint)

            withContext(Dispatchers.Main) {
                onAuthType(authType)
            }
        }
    }

    protected open fun onAuthType(authType: AuthType) {}
    open fun onPkceAuthCancelled() {}

    val pkceAuth = PkceAuth()
    inner class PkceAuth {
        private lateinit var authService: PkceAuthService

        /**
         * Recreates [authService] with provided [AuthConfig]
         * @throws [IllegalArgumentException]
         */
        fun initServiceWith(authConfig: AuthConfig, authState: String? = null) {
            var state: AuthState? = null
            if (authState != null) {
                state = AuthState.jsonDeserialize(authState)
            }

            authService = PkceAuthService(context, state, authConfig)
        }

        fun login(endpoint: String, activity: Activity, requestCode: Int) {
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    authService.initiateLogin(endpoint, activity, requestCode)
                } catch (ex: Exception) {
                    _onError.value = ex.message
                }
            }
        }

        fun reLogin(activity: Activity, requestCode: Int) {
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    authService.initiateReLogin(activity, requestCode)
                } catch (ex: Exception) {
                }
            }
        }

        fun handleActivityResult(intent: Intent) {
            viewModelScope.launch {

                val tokenResult = authService.getAuthResponse(intent)

                tokenResult.onSuccess {
                    val userEmail = authService.getUserEmail() ?: ""
                    _onCredentials.value = Credentials(userEmail, it, AuthType.PKCE.value)
                }

                tokenResult.onError {
                    _onError.value = it.message ?: ""
                }
            }
        }
    }
}

abstract class AuthenticationActivity<out T : AuthenticationViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(viewModel.onCredentials, ::onCredentials)
        observe(viewModel.onError, ::onError)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_AUTHENTICATE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                viewModel.onPkceAuthCancelled()
            } else {
                data?.let { viewModel.pkceAuth.handleActivityResult(it) }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun pkceLogin(endpoint: String) {
        if (viewModel.isReLogin) {
            viewModel.pkceAuth.reLogin(this, REQUEST_CODE_AUTHENTICATE)
        } else {
            viewModel.pkceAuth.login(endpoint, this, REQUEST_CODE_AUTHENTICATE)
        }
    }

    abstract fun onCredentials(credentials: Credentials)
    abstract fun onError(error: String)

    companion object {
        const val REQUEST_CODE_AUTHENTICATE = 20
    }
}

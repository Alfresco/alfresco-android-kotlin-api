package com.alfresco.auth.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthService
import com.alfresco.auth.AuthType
import com.alfresco.auth.Credentials
import com.alfresco.core.data.LiveEvent
import com.alfresco.core.data.MutableLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AuthenticationViewModel : ViewModel() {

    protected abstract var context: Context
    protected lateinit var authService: AuthService

    protected val _onAuthType = MutableLiveEvent<AuthType>()
    protected val _onCredentials = MutableLiveEvent<Credentials>()
    protected val _onError = MutableLiveEvent<String>()

    val onAuthType: LiveEvent<AuthType> get() = _onAuthType
    val onCredentials: LiveEvent<Credentials> get() = _onCredentials
    val onError: LiveEvent<String> get() = _onError

    /**
     * Recreates [authService] with provided [AuthConfig]
     * @throws [IllegalArgumentException]
     */
    protected fun initServiceWith(authConfig: AuthConfig) {
        authService = AuthService(context, null, authConfig)
    }

    fun checkAuthType(endpoint: String) {
        viewModelScope.launch {
            val authType = authService.getAuthType(endpoint)
            _onAuthType.value = authType
        }
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

    fun handleActivityResult(intent: Intent) {
        viewModelScope.launch {

            val tokenResult = authService.getAuthResponse(intent)

            tokenResult.onSuccess {
                val userEmail = authService.getUserEmail() ?: ""
                _onCredentials.value = Credentials.Sso(userEmail, it)
            }

            tokenResult.onError {
                _onError.value = it.message ?: ""
            }
        }
    }

    open fun handleAuthType(endpoint: String, authType: AuthType) {}
}

abstract class AuthenticationActivity<out T : AuthenticationViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(viewModel.onAuthType, ::onAuthType)
        observe(viewModel.onCredentials, ::onCredentials)
        observe(viewModel.onError, ::onError)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_AUTHENTICATE -> {
                data?.let { viewModel.handleActivityResult(it) }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun login(endpoint: String) {
        viewModel.login(endpoint, this, REQUEST_CODE_AUTHENTICATE)
    }

    protected open fun onAuthType(type: AuthType) {
    }

    abstract fun onCredentials(credentials: Credentials)
    abstract fun onError(error: String)

    companion object {
        const val REQUEST_CODE_AUTHENTICATE = 20
    }
}

package com.alfresco.auth.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
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
    abstract var authConfig: AuthConfig

    protected val authService by lazy { AuthService(context!!, null, authConfig) }
    protected val _isLoading = MutableLiveData<Boolean>()
    protected val _onCredentials = MutableLiveEvent<Credentials>()
    protected val _onError = MutableLiveEvent<String>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val onCredentials: LiveEvent<Credentials> get() = _onCredentials
    val onError: LiveEvent<String> get() = _onError

    fun checkAuthType(endpoint: String) {
        _isLoading.value = true

        viewModelScope.launch {
            // TODO: AuthConfig ? saving?
            val authType = authService.getAuthType(endpoint, authConfig)

            _isLoading.value = false

            handleAuthType(endpoint, authType)
        }
    }

    fun login(endpoint: String, activity: Activity, requestCode: Int) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            try {
                authService.initiateLogin(endpoint, activity, requestCode)
            } catch (ex: Exception) {
            }
        }
    }

    fun handleActivityResult(intent: Intent) {
        viewModelScope.launch {

            val tokenResult = authService.getAuthResponse(intent)

            _isLoading.value = false

            tokenResult.onSuccess {
                val userEmail = authService.getUserEmail() ?: ""
                _onCredentials.setValue(Credentials.Sso(userEmail, it))
            }

            tokenResult.onError {
                _onError.setValue(it.message ?: "")
            }
        }
    }

    open fun handleAuthType(endpoint: String, authType: AuthType) {}
}

abstract class AuthenticationActivity<out T : AuthenticationViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(viewModel.isLoading, ::onLoading)
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

    protected open fun onLoading(isLoading: Boolean) {
    }

    abstract fun onCredentials(credentials: Credentials)
    abstract fun onError(error: String)

    companion object {
        const val REQUEST_CODE_AUTHENTICATE = 20
    }
}

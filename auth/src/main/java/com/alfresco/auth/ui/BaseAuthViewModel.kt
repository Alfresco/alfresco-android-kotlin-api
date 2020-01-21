package com.alfresco.auth.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AlfrescoAuth
import com.alfresco.auth.AuthType
import com.alfresco.auth.GlobalAuthConfig
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseAuthViewModel : ViewModel() {

    protected abstract var globalAuthConfig: GlobalAuthConfig

    protected val pkceAuthService: PkceAuthService by lazy {

        val authService = PkceAuthService()
        authService.setGlobalAuthConfig(globalAuthConfig)

        authService
    }

    protected val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun updateAuthConfig(globalAuthConfig: GlobalAuthConfig) {
        pkceAuthService.setGlobalAuthConfig(globalAuthConfig)
    }

    fun checkAuthType(endpoint: String) {
        _isLoading.value = true

        viewModelScope.launch {
            val authType = AlfrescoAuth.getAuthType(endpoint, globalAuthConfig)

            _isLoading.value = false

            handleAuthType(endpoint, authType)
        }
    }

    fun login(endpoint: String, activity: Activity, requestCode: Int) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            try {
                pkceAuthService.initiateLogin(endpoint, activity, requestCode)

            } catch (ex: Exception) {
            }
        }
    }

    fun handleSSOResult(intent: Intent) {
        viewModelScope.launch {

            val tokenResult = pkceAuthService.getAuthResponse(intent)

            _isLoading.value = false

            tokenResult.onSuccess {
                handleSSOTokenResponse(PkceAuthUiModel(true, it.accessToken))
            }

            tokenResult.onError {
                handleSSOTokenResponse(PkceAuthUiModel(false, error = it.message))
            }
        }
    }

    open fun handleAuthType(endpoint: String, authType: AuthType) {}

    open fun handleSSOTokenResponse(model: PkceAuthUiModel) {}
}

data class PkceAuthUiModel(
    val success: Boolean,
    val accessToken: String? = null,
    val error: String? = null
)
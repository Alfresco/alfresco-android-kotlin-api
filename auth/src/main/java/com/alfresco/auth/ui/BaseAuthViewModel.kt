package com.alfresco.auth.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthService
import com.alfresco.auth.AuthType
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.pkce.PkceAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseAuthViewModel : ViewModel() {

    protected abstract var context: Context
    abstract var authConfig: AuthConfig
    protected val authService by lazy { AuthService(context!!, null, authConfig) }

    protected val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun checkAuthType(endpoint: String) {
        _isLoading.value = true

        viewModelScope.launch {
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

    fun handleSSOResult(intent: Intent) {
        viewModelScope.launch {

            val tokenResult = authService.getAuthResponse(intent)

            _isLoading.value = false

            tokenResult.onSuccess {

                val userEmail = authService.getUserEmail()
                handleSSOTokenResponse(PkceAuthUiModel(true, authState = it, userEmail = userEmail))
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

    val authState: String? = null,
    val userEmail: String? = null,

    val error: String? = null
)

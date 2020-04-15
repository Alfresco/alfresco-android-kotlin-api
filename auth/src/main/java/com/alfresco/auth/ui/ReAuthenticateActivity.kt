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
import com.alfresco.auth.AuthType
import com.alfresco.auth.Credentials
import com.alfresco.auth.pkce.PkceAuthService
import com.alfresco.core.data.LiveEvent
import com.alfresco.core.data.MutableLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import org.json.JSONException

open class ReAuthenticateViewModel(context: Context, authState: String, authConfig: AuthConfig) : ViewModel() {
    private val authService: PkceAuthService
    protected val _isLoading = MutableLiveData<Boolean>()
    protected val _onCredentials = MutableLiveEvent<Credentials>()
    protected val _onError = MutableLiveEvent<String>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val onCredentials: LiveEvent<Credentials> get() = _onCredentials
    val onError: LiveEvent<String> get() = _onError

    init {
        val state = try {
            AuthState.jsonDeserialize(authState)
        } catch (ex: JSONException) {
            null
        }
        authService = PkceAuthService(context, state, authConfig)
    }

    fun begin(activity: Activity, requestCode: Int) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            try {
                authService.initiateReLogin(activity, requestCode)
            } catch (ex: Exception) {
            }
        }
    }

    internal fun handleActivityResult(intent: Intent) {
        viewModelScope.launch {
            val tokenResult = authService.getAuthResponse(intent)
            _isLoading.value = false

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

abstract class ReAuthenticateActivity<out T : ReAuthenticateViewModel> : AppCompatActivity() {
    protected abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(viewModel.isLoading, ::onLoading)
        observe(viewModel.onCredentials, ::onCredentials)
        observe(viewModel.onError, ::onError)
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.isLoading.value != true) {
            viewModel.begin(this, REQUEST_CODE_RE_AUTHENTICATE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_RE_AUTHENTICATE &&
            resultCode == Activity.RESULT_OK) {
            data?.let { viewModel.handleActivityResult(data) }
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    protected open fun onLoading(isLoading: Boolean) {
    }

    abstract fun onCredentials(credentials: Credentials)
    abstract fun onError(error: String)

    companion object {
        private const val REQUEST_CODE_RE_AUTHENTICATE = 9
    }
}

package com.alfresco.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthType
import com.alfresco.auth.Credentials
import com.alfresco.auth.data.MutableLiveEvent
import com.alfresco.auth.ui.AuthenticationActivity
import com.alfresco.auth.ui.AuthenticationViewModel
import com.alfresco.auth.ui.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.connectButton
import kotlinx.android.synthetic.main.activity_login.serverInput

class LoginViewModel(override var context: Context) : AuthenticationViewModel() {

    var server: String = ""
    val onSsoLogin = MutableLiveEvent<String>()

    val applicationUrl: String
        get() = discoveryService.serviceDocumentsEndpoint(server).toString()

    fun connect() {
        try {
            checkAuthType(server, AuthConfig.defaultConfig)
        } catch (ex: Exception) {
            _onError.value = ex.message
        }
    }

    override suspend fun onAuthType(authType: AuthType) {
        if (authType == AuthType.PKCE) {
            pkceLogin()
        } else {
            _onError.value = context.getString(R.string.auth_error_check_connect_url)
        }
    }

    private fun pkceLogin() {
        // Provide authState if you want to re-authenticate on refresh token expiry
        pkceAuth.initServiceWith(AuthConfig.defaultConfig, null)

        try {
            // If identity and the content service is hosted separately provide the URI here.
            onSsoLogin.value = server
        } catch (ex: Exception) {
            _onError.value = ex.message
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class LoginActivity() : AuthenticationActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        connectButton.setOnClickListener {
            viewModel.server = serverInput.text.toString()
            viewModel.connect()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        observe(viewModel.onSsoLogin, ::pkceLogin)
    }

    override fun onCredentials(credentials: Credentials) {
        Account.createAccount(
            this,
            credentials.username,
            credentials.authState,
            credentials.authType,
            viewModel.applicationUrl
        )
        navigateToMain()
    }

    private fun navigateToMain() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
        finish()
    }

    override fun onError(error: String) {
        val parentLayout: View = findViewById(android.R.id.content)
        Snackbar.make(parentLayout,
            error,
            Snackbar.LENGTH_LONG).show()
    }
}

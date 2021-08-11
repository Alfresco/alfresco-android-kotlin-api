package com.alfresco.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthType
import com.alfresco.auth.Credentials
import com.alfresco.auth.data.AuthServerData
import com.alfresco.auth.ui.AuthenticationActivity
import com.alfresco.auth.ui.AuthenticationViewModel
import com.alfresco.sample.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginViewModel(override var context: Context) : AuthenticationViewModel() {

    var server: String = ""

    val applicationUrl: String
        get() = discoveryService.contentServiceUrl(server).toString()

    fun connect() {
        try {
            checkAuthType(server, AuthConfig.defaultConfig, this::onAuthType)
        } catch (ex: Exception) {
            _onError.value = ex.message
        }
    }

    private fun onAuthType(authServerData: AuthServerData) {
        if (authServerData.authType == AuthType.PKCE) {
            pkceLogin(server, AuthConfig.defaultConfig)
        } else {
            _onError.value = context.getString(R.string.auth_error_check_connect_url)
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

class LoginActivity : AuthenticationActivity<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory(applicationContext) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.connectButton.setOnClickListener {
            viewModel.server = binding.serverInput.text.toString()
            viewModel.connect()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
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

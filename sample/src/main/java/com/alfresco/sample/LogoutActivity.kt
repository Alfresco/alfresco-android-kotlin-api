package com.alfresco.sample

import android.content.Context
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthType
import com.alfresco.auth.ui.EndSessionActivity
import com.alfresco.auth.ui.EndSessionViewModel

class LogoutViewModel(context: Context, authType: AuthType?, authState: String, authConfig: AuthConfig) : EndSessionViewModel(context, authType, authState, authConfig) {

    companion object {
        fun build(context: Context): LogoutViewModel {
            val acc = requireNotNull(Account.getAccount(context))
            return LogoutViewModel(context, AuthType.PKCE, acc.authState, AuthConfig.defaultConfig)
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LogoutViewModel::class.java)) {
                return build(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

class LogoutActivity() : EndSessionActivity<LogoutViewModel>() {

    override val viewModel: LogoutViewModel by viewModels { LogoutViewModel.Factory(applicationContext) }
}

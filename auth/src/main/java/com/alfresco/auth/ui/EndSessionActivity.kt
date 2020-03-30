package com.alfresco.auth.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import org.json.JSONException

open class EndSessionViewModel(context: Context, authState: String, authConfig: AuthConfig) : ViewModel() {
    private val authService: AuthService

    init {
        val state = try { AuthState.jsonDeserialize(authState) } catch (ex: JSONException) { null }
        authService = AuthService(context, state, authConfig)
    }

    fun logout(activity: Activity, requestCode: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            authService.endSession(activity, requestCode)
        }
    }
}

abstract class EndSessionActivity<out T : EndSessionViewModel> : AppCompatActivity() {
    protected abstract val viewModel: T

    override fun onResume() {
        super.onResume()

        viewModel.logout(this, REQUEST_CODE_END_SESSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_END_SESSION) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(Activity.RESULT_CANCELED)
                finish()
            } else {
                // TODO: Test result
                setResult(RESULT_OK)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val REQUEST_CODE_END_SESSION = 1
    }
}

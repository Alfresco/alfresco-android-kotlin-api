package com.alfresco.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AlfrescoAuthActivity<out T : BaseAuthViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe(viewModel.isLoading, ::onLoading)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PKCE_REQUEST_CODE -> {
                data?.let { viewModel.handleSSOResult(it) }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun aimsLogin(endpoint: String) {
        viewModel.login(endpoint, this, PKCE_REQUEST_CODE)
    }

    protected open fun onLoading(isLoading: Boolean) {}

    companion object {

        const val PKCE_REQUEST_CODE = 20
    }
}

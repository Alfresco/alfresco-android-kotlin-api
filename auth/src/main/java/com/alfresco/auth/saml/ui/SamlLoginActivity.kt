package com.alfresco.auth.saml.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import com.alfresco.auth.R
import kotlinx.android.synthetic.main.activity_login_saml.*

/**
 * Created by Bogdan Roatis on 7/24/2019.
 */
class SamlLoginActivity : FragmentActivity() {

    private lateinit var loginWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_saml)

        val url = intent.getStringExtra(URL)

        loginWebView = findViewById(R.id.webview)
        loginWebView.applyDefaultSettings()
        loginWebView.listenForCredentials(::stopLoading) {
            val data = Intent().apply { putExtra(SAML, it) }
            setResult(RESULT_OK, data)
            finish()
        }

        loginWebView.loadUrl(url)
    }

    private fun stopLoading() {
        progress.visibility = View.GONE
    }

    companion object {
        /**
         * Private constant used as a key for the url
         */
        private const val URL = "url"
        public const val SAML = "saml_credentials"

        fun show(activity: Activity, url: String, requestCode: Int) {
            val intent = Intent(activity, SamlLoginActivity::class.java).apply {
                putExtra(URL, url)
            }

            activity.startActivityForResult(intent, requestCode)
        }
    }
}

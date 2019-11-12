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
class SamlLoginActivity : FragmentActivity(R.layout.activity_login_saml) {

    private lateinit var loginWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(URL)

        loginWebView = findViewById(R.id.webview)
        loginWebView.run {
            applyDefaultSettings()
            listenForCredentials(this@SamlLoginActivity::stopLoading) {
                val data = Intent().apply { putExtra(SAML, it) }
                setResult(RESULT_OK, data)
                finish()
            }

            loadUrl(url)
        }
    }

    private fun stopLoading() {
        progress.visibility = View.GONE
    }

    companion object {
        /**
         * Private constant used as a key for the url
         */
        private const val URL = "url"
        const val SAML = "saml_credentials"

        fun show(activity: Activity, url: String, requestCode: Int) {
            val intent = Intent(activity, SamlLoginActivity::class.java).apply {
                putExtra(URL, url)
            }

            activity.startActivityForResult(intent, requestCode)
        }
    }
}

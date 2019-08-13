package com.alfresco.auth.saml.ui

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alfresco.auth.saml.data.SamlCredentials
import com.alfresco.auth.saml.data.getSamlResponse

/**
 * Created by Bogdan Roatis on 7/23/2019.
 */

@SuppressLint("SetJavaScriptEnabled")
fun WebView.applyDefaultSettings() {
    settings.apply {
        cacheMode = WebSettings.LOAD_NO_CACHE
        setAppCacheEnabled(false)
        domStorageEnabled = true
        databaseEnabled = true
        builtInZoomControls = true
        javaScriptEnabled = true
    }
    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
}

fun WebView.listenForCredentials(onPageFinished: () -> Unit, samlFunction: (SamlCredentials) -> Unit) {
    webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            onPageFinished()
            getSamlResponse(url)?.let {
                samlFunction(it)
            }

            super.onPageFinished(view, url)
        }
    }
}

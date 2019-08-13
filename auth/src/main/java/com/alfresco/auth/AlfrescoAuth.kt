package com.alfresco.auth

import android.app.Activity
import com.alfresco.auth.saml.data.AuthType
import com.alfresco.auth.saml.data.SAML_LOGIN_GATEWAY_URL
import com.alfresco.auth.saml.ui.SamlLoginActivity

/**
 * Created by Bogdan Roatis on 7/23/2019.
 */
object AlfrescoAuth {

    fun showUI(authType: AuthType, activity: Activity, requestCode: Int, url: String? = null) {
        when (authType) {
            AuthType.SAML -> startSamlFlow(activity, requestCode, url ?: SAML_LOGIN_GATEWAY_URL)
        }
    }

    private fun startSamlFlow(activity: Activity, requestCode: Int, url: String) {
        SamlLoginActivity.show(activity, url, requestCode)
    }
}

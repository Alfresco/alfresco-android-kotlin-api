package com.alfresco.sample

import com.alfresco.auth.AuthConfig

val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = true,
        port = "443",
        clientId = "alfresco-android-acs-app",
        realm = "alfresco",
        redirectUrl = "androidacsapp://aims/auth",
        contentServicePath = "alfresco"
    )

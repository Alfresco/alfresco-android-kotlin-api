package com.alfresco.sample

import com.alfresco.auth.AuthConfig

val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = false,
        port = "80",
        clientId = "alfresco-android-acs-app",
        realm = "alfresco",
        redirectUrl = "androidacsapp://aims/auth",
        serviceDocuments = "alfresco"
    )

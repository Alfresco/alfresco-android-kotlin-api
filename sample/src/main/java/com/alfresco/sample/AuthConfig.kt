package com.alfresco.sample

import com.alfresco.auth.AuthConfig

val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = false, // set true to enable https otherwise false
        port = "", // Add port number
        clientId = "", // Add client Id
        realm = "", // Add realm path
        redirectUrl = "", // Add redirect URL scheme
        contentServicePath = ""
    )
/*val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = true,
        port = "443",
        clientId = "alfresco-android-acs-app",
        realm = "alfresco",
        redirectUrl = "androidacsapp://aims/auth",
        contentServicePath = "alfresco"
    )*/

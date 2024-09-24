package com.alfresco.sample

import com.alfresco.auth.AuthConfig

val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = true,
        port = "443",
        clientId = "zTSfC3VEcrguvP5CKIgNIBCOTliawIKg",
        realm = "",
        redirectUrl = "demo://dev-ps-alfresco.auth0.com/android/com.alfresco.sample/callback",
        contentServicePath = "alfresco"
    )
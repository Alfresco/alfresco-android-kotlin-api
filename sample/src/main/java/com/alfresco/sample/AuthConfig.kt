package com.alfresco.sample

import com.alfresco.auth.AuthConfig

val AuthConfig.Companion.defaultConfig: AuthConfig
    get() = AuthConfig(
        https = false, // set true to enable https otherwise false
        port = "", // Add port number
        clientId = "", // Add client Id
        realm = "", // Add realm path
        redirectUrl = "", // Add redirect URL scheme
        contentServicePath = "" // Add path for content service
    )

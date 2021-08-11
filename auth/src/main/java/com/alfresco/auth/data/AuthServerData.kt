package com.alfresco.auth.data

import com.alfresco.auth.AuthType

data class AuthServerData(val authType: AuthType, val isEnterprise: Boolean)

package com.alfresco.auth

sealed class Credentials {
    data class Basic(val username: String, val password: String) : Credentials()
    data class Sso(val username: String, val authState: String) : Credentials()
}

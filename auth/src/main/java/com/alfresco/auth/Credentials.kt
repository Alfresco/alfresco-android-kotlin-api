package com.alfresco.auth

data class Credentials(
    val username: String,
    val authState: String,
    val authType: String
)

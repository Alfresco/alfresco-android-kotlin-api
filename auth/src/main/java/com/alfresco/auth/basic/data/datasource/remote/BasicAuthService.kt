package com.alfresco.auth.basic.data.datasource.remote

import com.alfresco.auth.basic.config.BasicAuthConfig
import com.alfresco.auth.basic.data.datasource.remote.entities.RemoteBasicAuthCredentials
import com.alfresco.auth.basic.data.entities.BasicAuthCredentials
import com.alfresco.core.data.Result
import com.alfresco.core.extension.isBlankOrEmpty
import com.alfresco.core.mapper.EntityMapper
import com.alfresco.core.network.Alfresco
import com.alfresco.core.network.request.responseCustom
import java.util.*


/**
 * Created by Bogdan Roatis on 8/6/2019.
 */
class BasicAuthService(
        private val entityMapper: EntityMapper<RemoteBasicAuthCredentials, BasicAuthCredentials>
) {

    suspend fun login(username: String,
                      password: String,
                      basicAuthConfig: BasicAuthConfig
    ) =
            generateRequestAndCall(username, password, basicAuthConfig)
                    .map { entityMapper.mapToEntity(this) }

    private suspend fun generateRequestAndCall(username: String,
                                               password: String,
                                               basicAuthConfig: BasicAuthConfig
    ) =
            try {
                Alfresco.with(generateEndpoint(basicAuthConfig))
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Accept", "application/json")
                        .body(generateBody(username, password, basicAuthConfig.clientId, basicAuthConfig.clientSecret))
                        .post()
                        .responseCustom<RemoteBasicAuthCredentials>()
            } catch (exception: Exception) {
                Result.error(exception)
            }

    private fun generateBody(username: String, password: String, clientId: String, clientSecret: String) =
            "grant_type=$GRANT_TYPE&username=$username&password=$password&client_id=$clientId&client_secret=$clientSecret"

    /**
     * Used for checking if the [BasicAuthConfig] has correct data
     */
    private fun check(basicAuthConfig: BasicAuthConfig) {
        basicAuthConfig.run {
            require(!baseUrl.isBlankOrEmpty()) { "BaseUrl is blank or empty" }
            require(!clientId.isBlankOrEmpty()) { "Client id is blank or empty" }
            require(!realm.isBlankOrEmpty()) { "Realm is blank or empty" }
            require(!clientSecret.isBlankOrEmpty()) { "Client secret is blank or empty" }
        }
    }

    /**
     * Generates the endpoint needed for the basic auth
     */
    private fun generateEndpoint(basicAuthConfig: BasicAuthConfig): String {
        with(basicAuthConfig) {
            check(this).also {
                return "http://$baseUrl/auth/realms/$realm/protocol/openid-connect/token"
            }
        }
    }

    companion object {
        const val GRANT_TYPE = "password"
    }
}

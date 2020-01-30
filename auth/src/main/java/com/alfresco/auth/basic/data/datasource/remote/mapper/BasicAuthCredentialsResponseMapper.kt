package com.alfresco.auth.basic.data.datasource.remote.mapper

import com.alfresco.auth.basic.data.datasource.remote.entities.RemoteBasicAuthCredentials
import com.alfresco.auth.basic.data.entities.BasicAuthCredentials
import com.alfresco.core.mapper.EntityMapper

/**
 * Created by Bogdan Roatis on 9/23/2019.
 */
class BasicAuthCredentialsResponseMapper : EntityMapper<RemoteBasicAuthCredentials, BasicAuthCredentials> {

    override fun mapFromEntity(entity: BasicAuthCredentials): RemoteBasicAuthCredentials {
        throw UnsupportedOperationException()
    }

    override fun mapToEntity(dataSourceEntity: RemoteBasicAuthCredentials) =
        dataSourceEntity.run {
            BasicAuthCredentials(
                scope,
                token_type,
                `not-before-policy`,
                session_state,
                refresh_token,
                refresh_expires_in,
                expires_in,
                access_token
            )
        }
}

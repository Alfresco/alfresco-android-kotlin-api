package com.alfresco.content.apis

import com.alfresco.content.models.AppConfigModel
import retrofit2.http.GET
import retrofit2.http.Headers

@JvmSuppressWildcards
interface AppConfigApi {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("app.config.json")
    suspend fun getAppConfig() :AppConfigModel

}
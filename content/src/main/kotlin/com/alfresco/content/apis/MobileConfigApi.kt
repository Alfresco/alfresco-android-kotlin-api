package com.alfresco.content.apis

import com.alfresco.content.models.MobileConfigData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

@JvmSuppressWildcards
interface MobileConfigApi {

    @Headers(
        "Content-Type: application/json",
    )
    @GET
    suspend fun getMobileConfig(@Url url: String): MobileConfigData
}

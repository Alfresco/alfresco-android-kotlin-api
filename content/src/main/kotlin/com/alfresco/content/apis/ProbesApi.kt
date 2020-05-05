/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.Error
import com.alfresco.content.models.ProbeEntry
import retrofit2.http.GET
import retrofit2.http.Headers

@JvmSuppressWildcards
interface ProbesApi {
    /**
     * Check readiness and liveness of the repository
     * **Note:** this endpoint is available in Alfresco 6.0 and newer versions.  Returns a status of 200 to indicate success and 503 for failure.  The readiness probe is normally only used to check repository startup.  The liveness probe should then be used to check the repository is still responding to requests.  **Note:** No authentication is required to call this endpoint. 
     * The endpoint is owned by defaultname service owner
     * @param probeId The name of the probe: * -ready- * -live-  (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("probes/{probeId}")
    suspend fun getProbe(
        @retrofit2.http.Path("probeId") probeId: String
    ): ProbeEntry
}

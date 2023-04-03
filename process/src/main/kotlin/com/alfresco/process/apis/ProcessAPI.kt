package com.alfresco.process.apis

import com.alfresco.process.models.ContentDataEntry
import com.alfresco.process.models.RequestLinkContent
import com.alfresco.process.models.RequestProcessInstances
import com.alfresco.process.models.ResultListProcessDefinitions
import com.alfresco.process.models.ResultListProcessInstances
import com.alfresco.process.models.SystemProperties
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Marked as ProcessAPI interface
 */

@JvmSuppressWildcards
interface ProcessAPI {

    /**
     * Api to upload the content
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/content")
    suspend fun linkContentToProcess(@Body requestContent: RequestLinkContent): ContentDataEntry

    /**
     * Api to fetch all the process definitions
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/runtime-app-definitions")
    suspend fun processDefinitions(): ResultListProcessDefinitions

    /**
     * Api to fetch single process definition
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/process-definitions")
    suspend fun singleProcessDefinition(@Query("latest") latest: Boolean, @Query("appDefinitionId") appDefinitionId: String): ResultListProcessDefinitions

    /**
     * Api to fetch all the process Instances
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/process-instances/query")
    suspend fun processInstances(@Body requestProcessInstances: RequestProcessInstances): ResultListProcessInstances

    /**
     * Api to fetch system properties
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/system/properties")
    suspend fun getSystemProperties(): SystemProperties
}

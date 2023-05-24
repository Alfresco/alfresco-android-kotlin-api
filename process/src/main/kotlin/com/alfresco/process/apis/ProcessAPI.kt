package com.alfresco.process.apis

import com.alfresco.process.models.ContentDataEntry
import com.alfresco.process.models.ProcessInstanceEntry
import com.alfresco.process.models.RequestLinkContent
import com.alfresco.process.models.RequestProcessInstances
import com.alfresco.process.models.RequestProcessInstancesQuery
import com.alfresco.process.models.ResultAccountInfo
import com.alfresco.process.models.ResultGroupsList
import com.alfresco.process.models.ResultListProcessDefinitions
import com.alfresco.process.models.ResultListProcessInstances
import com.alfresco.process.models.ResultListRuntimeProcessDefinitions
import com.alfresco.process.models.ResultStartForm
import com.alfresco.process.models.SystemProperties
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
    suspend fun processDefinitions(): ResultListRuntimeProcessDefinitions

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
    suspend fun processInstancesQuery(@Body requestProcessInstancesQuery: RequestProcessInstancesQuery): ResultListProcessInstances

    /**
     * Api to fetch system properties
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/system/properties")
    suspend fun getSystemProperties(): SystemProperties

    /**
     * Api to search the groups
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/groups")
    suspend fun searchGroups(@Query("filter") latest: String): ResultGroupsList

    /**
     * Api to create and start the workflow
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/process-instances")
    suspend fun createProcessInstance(@Body requestProcessInstances: RequestProcessInstances): ProcessInstanceEntry

    /**
     * Api to upload the content on process
     */
    @Multipart
    @POST("api/enterprise/content/raw")
    suspend fun uploadRawContent(@Part multipartBody: MultipartBody.Part): ContentDataEntry

    /**
     * Api to fetch the start form representation
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/process-definitions/{processDefinitionId}/start-form")
    suspend fun startForm(@Path("processDefinitionId") processDefinitionId: String): ResultStartForm

    /**
     * Api to fetch the account profile info
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/profile/accounts/alfresco")
    suspend fun accountInfo(): ResultAccountInfo
}

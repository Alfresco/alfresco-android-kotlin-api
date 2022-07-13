package com.alfresco.process.apis

import com.alfresco.process.models.RequestTaskFilters
import com.alfresco.process.models.ResultList
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Marked as TaskAPI interface
 */
@JvmSuppressWildcards
interface TaskAPI {

    /**
     * Api to fetch all the tasks
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/tasks/query")
    suspend fun taskList(@Body filters: RequestTaskFilters): ResultList
}

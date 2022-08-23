package com.alfresco.process.apis

import com.alfresco.process.models.CommentDataEntry
import com.alfresco.process.models.RequestComment
import com.alfresco.process.models.RequestTaskFilters
import com.alfresco.process.models.ResultComments
import com.alfresco.process.models.ResultContents
import com.alfresco.process.models.ResultList
import com.alfresco.process.models.TaskDataEntry
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * Api to fetch the task details by taskID
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/tasks/{task_id}")
    suspend fun getTaskDetails(@Path("task_id") taskID: String): TaskDataEntry

    /**
     * Api to fetch the comments by taskID
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/tasks/{task_id}/comments")
    suspend fun getComments(@Path("task_id") taskID: String): ResultComments

    /**
     * Api to add comment on the given taskID
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/tasks/{task_id}/comments")
    suspend fun addComment(@Path("task_id") taskID: String, @Body requestComment: RequestComment): CommentDataEntry

    /**
     * Api to fetch the contents by taskID
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/tasks/{task_id}/content")
    suspend fun getContents(@Path("task_id") taskID: String, @Query("isRelatedContent") isRelatedContent: Boolean = true): ResultContents

    /**
     * Api to complete the task
     */
    @Headers("Content-type: application/json")
    @PUT("api/enterprise/tasks/{task_id}/action/complete")
    suspend fun completeTask(@Path("task_id") taskID: String): Response<Unit>

    /**
     * Api to get the raw contents
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/content/{task_id}/raw")
    suspend fun getRawContent(@Path("task_id") taskID: String): Response<Unit>
}

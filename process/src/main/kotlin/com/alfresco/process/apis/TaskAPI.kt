package com.alfresco.process.apis

import com.alfresco.process.models.AssignUserBody
import com.alfresco.process.models.CommentDataEntry
import com.alfresco.process.models.ContentDataEntry
import com.alfresco.process.models.ProfileData
import com.alfresco.process.models.RequestComment
import com.alfresco.process.models.RequestOutcomes
import com.alfresco.process.models.RequestSaveForm
import com.alfresco.process.models.RequestTaskFilters
import com.alfresco.process.models.ResultComments
import com.alfresco.process.models.ResultContents
import com.alfresco.process.models.ResultForm
import com.alfresco.process.models.ResultFormVariables
import com.alfresco.process.models.ResultList
import com.alfresco.process.models.ResultUserList
import com.alfresco.process.models.TaskBodyCreate
import com.alfresco.process.models.TaskDataEntry
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    /**
     * Api to get the create task
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/tasks")
    suspend fun createTask(@Body taskBodyCreate: TaskBodyCreate): TaskDataEntry

    /**
     * Api to update the task
     */
    @Headers("Content-type: application/json")
    @PUT("api/enterprise/tasks/{task_id}")
    suspend fun updateTask(@Path("task_id") taskID: String, @Body taskBodyCreate: TaskBodyCreate): TaskDataEntry

    /**
     * Api to assign task to other user
     */
    @Headers("Content-type: application/json")
    @PUT("api/enterprise/tasks/{task_id}/action/assign")
    suspend fun assignUser(@Path("task_id") taskID: String, @Body assignUserBody: AssignUserBody): TaskDataEntry

    /**
     * Api to search user on the process services
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/users")
    suspend fun searchUser(@Query("filter") filter: String, @Query("email") email: String): ResultUserList

    /**
     * Api to upload the content for the give task id
     */
    @Multipart
    @POST("api/enterprise/tasks/{task_id}/raw-content")
    suspend fun uploadRawContent(
        @Path("task_id") taskID: String,
        @Part multipartBody: MultipartBody.Part,
        @Query("isRelatedContent") isRelatedContent: Boolean = true
    ): ContentDataEntry

    /**
     * Api to get the user profile
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/profile")
    suspend fun getProfile(): ProfileData

    /**
     * Api to delete the content
     */
    @Headers("Content-type: application/json")
    @DELETE("api/enterprise/content/{content_id}")
    suspend fun deleteRawContent(@Path("content_id") contentId: String): Response<Unit>

    /**
     * Api to get the task-form detail
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/task-forms/{task_id}")
    suspend fun taskForm(@Path("task_id") taskID: String): ResultForm

    /**
     * Api to save the form
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/task-forms/{task_id}/save-form")
    suspend fun saveForm(@Path("task_id") taskId: String, @Body saveFrom: RequestSaveForm): Response<Unit>

    /**
     * Api to perform the action on outcomes
     */
    @Headers("Content-type: application/json")
    @POST("api/enterprise/task-forms/{task_id}")
    suspend fun taskFormAction(@Path("task_id") taskId: String, @Body requestOutcome: RequestOutcomes): Response<Unit>

    /**
     * Api to perform the claim task
     */
    @Headers("Content-type: application/json")
    @PUT("api/enterprise/tasks/{task_id}/action/claim")
    suspend fun claimTask(@Path("task_id") taskId: String): Response<Unit>

    /**
     * Api to perform the un-claim task
     */
    @Headers("Content-type: application/json")
    @PUT("api/enterprise/tasks/{task_id}/action/unclaim")
    suspend fun unclaimTask(@Path("task_id") taskId: String): Response<Unit>

    /**
     * Api to get the task-form variables
     */
    @Headers("Content-type: application/json")
    @GET("api/enterprise/task-forms/{task_id}/variables")
    suspend fun taskFormVariables(@Path("task_id") taskId: String): List<ResultFormVariables>
}

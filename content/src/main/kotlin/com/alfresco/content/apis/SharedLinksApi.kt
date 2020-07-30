/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.Error
import com.alfresco.content.models.RenditionEntry
import com.alfresco.content.models.RenditionPaging
import com.alfresco.content.models.SharedLinkBodyCreate
import com.alfresco.content.models.SharedLinkBodyEmail
import com.alfresco.content.models.SharedLinkEntry
import com.alfresco.content.models.SharedLinkPaging
import com.alfresco.content.tools.CSV
import java.io.File
import java.time.ZonedDateTime
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface SharedLinksApi {
    /**
     * Create a shared link to a file
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Create a shared link to the file **nodeId** in the request body. Also, an optional expiry date could be set, so the shared link would become invalid when the expiry date is reached. For example:  ```JSON   {     \"nodeId\": \"1ff9da1a-ee2f-4b9c-8c34-3333333333\",     \"expiresAt\": \"2017-03-23T23:00:00.000+0000\"   } ```  **Note:** You can create shared links to more than one file  specifying a list of **nodeId**s in the JSON body like this:  ```JSON [   {     \"nodeId\": \"1ff9da1a-ee2f-4b9c-8c34-4444444444\"   },   {                 \"nodeId\": \"1ff9da1a-ee2f-4b9c-8c34-5555555555\"   } ] ``` If you specify a list as input, then a paginated list rather than an entry is returned in the response body. For example:  ```JSON {   \"list\": {     \"pagination\": {       \"count\": 2,       \"hasMoreItems\": false,       \"totalItems\": 2,       \"skipCount\": 0,       \"maxItems\": 100     },     \"entries\": [       {         \"entry\": {           ...         }       },       {         \"entry\": {           ...         }       }     ]   } } ``` 
     * The endpoint is owned by defaultname service owner
     * @param sharedLinkBodyCreate The nodeId to create a shared link for. (required)
     * @param include Returns additional information about the shared link, the following optional fields can be requested: * allowableOperations * path * properties * isFavorite * aspectNames  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/shared-links")
    suspend fun createSharedLink(
        @retrofit2.http.Body sharedLinkBodyCreate: SharedLinkBodyCreate,
        @retrofit2.http.Query("include") @CSV include: List<String>?,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): SharedLinkEntry
    /**
     * Deletes a shared link
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Deletes the shared link with identifier **sharedId**. 
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("alfresco/versions/1/shared-links/{sharedId}")
    suspend fun deleteSharedLink(
        @retrofit2.http.Path("sharedId") sharedId: String
    ): Unit
    /**
     * Email shared link
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Sends email with app-specific url including identifier **sharedId**.  The client and recipientEmails properties are mandatory in the request body. For example, to email a shared link with minimum info: ```JSON {     \"client\": \"myClient\",     \"recipientEmails\": [\"john.doe@acme.com\", \"joe.bloggs@acme.com\"] } ``` A plain text message property can be optionally provided in the request body to customise the sent email. Also, a locale property can be optionally provided in the request body to send the emails in a particular language (if the locale is supported by Alfresco). For example, to email a shared link with a messages and a locale: ```JSON {     \"client\": \"myClient\",     \"recipientEmails\": [\"john.doe@acme.com\", \"joe.bloggs@acme.com\"],     \"message\": \"myMessage\",     \"locale\":\"en-GB\" } ``` **Note:** The client must be registered before you can send a shared link email. See [server documentation]. However, out-of-the-box  share is registered as a default client, so you could pass **share** as the client name: ```JSON {     \"client\": \"share\",     \"recipientEmails\": [\"john.doe@acme.com\"] } ``` 
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     * @param sharedLinkBodyEmail The shared link email to send. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/shared-links/{sharedId}/email")
    suspend fun emailSharedLink(
        @retrofit2.http.Path("sharedId") sharedId: String,
        @retrofit2.http.Body sharedLinkBodyEmail: SharedLinkBodyEmail
    ): Unit
    /**
     * Get a shared link
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets minimal information for the file with shared link identifier **sharedId**.  **Note:** No authentication is required to call this endpoint. 
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links/{sharedId}")
    suspend fun getSharedLink(
        @retrofit2.http.Path("sharedId") sharedId: String,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): SharedLinkEntry
    /**
     * Get shared link content
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the content of the file with shared link identifier **sharedId**.  **Note:** No authentication is required to call this endpoint. 
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     * @param attachment **true** enables a web browser to download the file as an attachment. **false** means a web browser may preview the file in a new tab or window, but not download the file.  You can only set this parameter to **false** if the content type of the file is in the supported list; for example, certain image files and PDF files.  If the content type is not supported for preview, then a value of **false**  is ignored, and the attachment will be returned in the response.  (optional, default to true)
     * @param ifModifiedSince Only returns the content if it has been modified since the date provided. Use the date format defined by HTTP. For example, &#x60;Wed, 09 Mar 2016 16:56:34 GMT&#x60;.  (optional)
     * @param range The Range header indicates the part of a document that the server should return. Single part request supported, for example: bytes&#x3D;1-10.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links/{sharedId}/content")
    suspend fun getSharedLinkContent(
        @retrofit2.http.Path("sharedId") sharedId: String,
        @retrofit2.http.Query("attachment") attachment: Boolean?,
        @retrofit2.http.Header("If-Modified-Since") ifModifiedSince: ZonedDateTime?,
        @retrofit2.http.Header("Range") range: String?
    ): ResponseBody
    /**
     * Get shared link rendition information
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets rendition information for the file with shared link identifier **sharedId**.  This API method returns rendition information where the rendition status is CREATED,  which means the rendition is available to view/download.  **Note:** No authentication is required to call this endpoint.       
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     * @param renditionId The name of a thumbnail rendition, for example *doclib*, or *pdf*. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links/{sharedId}/renditions/{renditionId}")
    suspend fun getSharedLinkRendition(
        @retrofit2.http.Path("sharedId") sharedId: String,
        @retrofit2.http.Path("renditionId") renditionId: String
    ): RenditionEntry
    /**
     * Get shared link rendition content
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the rendition content for file with shared link identifier **sharedId**.  **Note:** No authentication is required to call this endpoint. 
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     * @param renditionId The name of a thumbnail rendition, for example *doclib*, or *pdf*. (required)
     * @param attachment **true** enables a web browser to download the file as an attachment. **false** means a web browser may preview the file in a new tab or window, but not download the file.  You can only set this parameter to **false** if the content type of the file is in the supported list; for example, certain image files and PDF files.  If the content type is not supported for preview, then a value of **false**  is ignored, and the attachment will be returned in the response.  (optional, default to true)
     * @param ifModifiedSince Only returns the content if it has been modified since the date provided. Use the date format defined by HTTP. For example, &#x60;Wed, 09 Mar 2016 16:56:34 GMT&#x60;.  (optional)
     * @param range The Range header indicates the part of a document that the server should return. Single part request supported, for example: bytes&#x3D;1-10.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links/{sharedId}/renditions/{renditionId}/content")
    suspend fun getSharedLinkRenditionContent(
        @retrofit2.http.Path("sharedId") sharedId: String,
        @retrofit2.http.Path("renditionId") renditionId: String,
        @retrofit2.http.Query("attachment") attachment: Boolean?,
        @retrofit2.http.Header("If-Modified-Since") ifModifiedSince: ZonedDateTime?,
        @retrofit2.http.Header("Range") range: String?
    ): ResponseBody
    /**
     * List renditions for a shared link
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of the rendition information for the file with shared link identifier **sharedId**.  This API method returns rendition information, including the rendition id, for each rendition where the rendition status is CREATED, which means the rendition is available to view/download.  **Note:** No authentication is required to call this endpoint.       
     * The endpoint is owned by defaultname service owner
     * @param sharedId The identifier of a shared link to a file. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links/{sharedId}/renditions")
    suspend fun listSharedLinkRenditions(
        @retrofit2.http.Path("sharedId") sharedId: String
    ): RenditionPaging
    /**
     * List shared links
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Get a list of links that the current user has read permission on source node.  The list is ordered in descending modified order.  **Note:** The list of links is eventually consistent so newly created shared links may not appear immediately. 
     * The endpoint is owned by defaultname service owner
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param where Optionally filter the list by \&quot;sharedByUser\&quot; userid of person who shared the link (can also use -me-)  *   &#x60;&#x60;&#x60;where&#x3D;(sharedByUser&#x3D;&#39;jbloggs&#39;)&#x60;&#x60;&#x60;  *   &#x60;&#x60;&#x60;where&#x3D;(sharedByUser&#x3D;&#39;-me-&#39;)&#x60;&#x60;&#x60;  (optional)
     * @param include Returns additional information about the shared link, the following optional fields can be requested: * allowableOperations * path * properties * isFavorite * aspectNames  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/shared-links")
    suspend fun listSharedLinks(
        @retrofit2.http.Query("skipCount") skipCount: Int?,
        @retrofit2.http.Query("maxItems") maxItems: Int?,
        @retrofit2.http.Query("where") where: String?,
        @retrofit2.http.Query("include") @CSV include: List<String>?,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): SharedLinkPaging
}
/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.DeletedNodeBodyRestore
import com.alfresco.content.models.DeletedNodeEntry
import com.alfresco.content.models.DeletedNodesPaging
import com.alfresco.content.models.Error
import com.alfresco.content.models.NodeEntry
import com.alfresco.content.models.RenditionEntry
import com.alfresco.content.models.RenditionPaging
import com.alfresco.content.tools.CSV
import java.io.File
import okhttp3.ResponseBody
import org.threeten.bp.ZonedDateTime
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface TrashcanApi {
    /**
     * Permanently delete a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Permanently deletes the deleted node **nodeId**. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("alfresco/versions/1/deleted-nodes/{nodeId}")
    suspend fun deleteDeletedNode(
        @retrofit2.http.Path("nodeId") nodeId: String
    ): Unit
    /**
     * Get rendition information for a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the rendition information for **renditionId** of file **nodeId**. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param renditionId The name of a thumbnail rendition, for example *doclib*, or *pdf*. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes/{nodeId}/renditions/{renditionId}")
    suspend fun getArchivedNodeRendition(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Path("renditionId") renditionId: String
    ): RenditionEntry
    /**
     * Get rendition content of a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the rendition content for **renditionId** of file **nodeId**. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param renditionId The name of a thumbnail rendition, for example *doclib*, or *pdf*. (required)
     * @param attachment **true** enables a web browser to download the file as an attachment. **false** means a web browser may preview the file in a new tab or window, but not download the file.  You can only set this parameter to **false** if the content type of the file is in the supported list; for example, certain image files and PDF files.  If the content type is not supported for preview, then a value of **false**  is ignored, and the attachment will be returned in the response.  (optional, default to true)
     * @param ifModifiedSince Only returns the content if it has been modified since the date provided. Use the date format defined by HTTP. For example, &#x60;Wed, 09 Mar 2016 16:56:34 GMT&#x60;.  (optional)
     * @param range The Range header indicates the part of a document that the server should return. Single part request supported, for example: bytes&#x3D;1-10.  (optional)
     * @param placeholder If **true** and there is no rendition for this **nodeId** and **renditionId**, then the placeholder image for the mime type of this rendition is returned, rather than a 404 response.  (optional, default to false)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes/{nodeId}/renditions/{renditionId}/content")
    suspend fun getArchivedNodeRenditionContent(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Path("renditionId") renditionId: String,
        @retrofit2.http.Query("attachment") attachment: Boolean?,
        @retrofit2.http.Header("If-Modified-Since") ifModifiedSince: ZonedDateTime?,
        @retrofit2.http.Header("Range") range: String?,
        @retrofit2.http.Query("placeholder") placeholder: Boolean?
    ): ResponseBody
    /**
     * Get a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the specific deleted node **nodeId**. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param include Returns additional information about the node. The following optional fields can be requested: * allowableOperations * association * isLink * isFavorite * isLocked * path * permissions  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes/{nodeId}")
    suspend fun getDeletedNode(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Query("include") @CSV include: List<String>?
    ): DeletedNodeEntry
    /**
     * Get deleted node content
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets the content of the deleted node with identifier **nodeId**. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param attachment **true** enables a web browser to download the file as an attachment. **false** means a web browser may preview the file in a new tab or window, but not download the file.  You can only set this parameter to **false** if the content type of the file is in the supported list; for example, certain image files and PDF files.  If the content type is not supported for preview, then a value of **false**  is ignored, and the attachment will be returned in the response.  (optional, default to true)
     * @param ifModifiedSince Only returns the content if it has been modified since the date provided. Use the date format defined by HTTP. For example, &#x60;Wed, 09 Mar 2016 16:56:34 GMT&#x60;.  (optional)
     * @param range The Range header indicates the part of a document that the server should return. Single part request supported, for example: bytes&#x3D;1-10.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes/{nodeId}/content")
    suspend fun getDeletedNodeContent(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Query("attachment") attachment: Boolean?,
        @retrofit2.http.Header("If-Modified-Since") ifModifiedSince: ZonedDateTime?,
        @retrofit2.http.Header("Range") range: String?
    ): ResponseBody
    /**
     * List renditions for a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of the rendition information for each rendition of the file **nodeId**, including the rendition id.  Each rendition returned has a **status**: CREATED means it is available to view or download, NOT_CREATED means the rendition can be requested.  You can use the **where** parameter to filter the returned renditions by **status**. For example, the following **where** clause will return just the CREATED renditions:  ``` (status='CREATED') ``` 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param where A string to restrict the returned objects by using a predicate. (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes/{nodeId}/renditions")
    suspend fun listDeletedNodeRenditions(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Query("where") where: String?
    ): RenditionPaging
    /**
     * List deleted nodes
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of deleted nodes for the current user.  If the current user is an administrator deleted nodes for all users will be returned.  The list of deleted nodes will be ordered with the most recently deleted node at the top of the list. 
     * The endpoint is owned by defaultname service owner
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param include Returns additional information about the node. The following optional fields can be requested: * allowableOperations * aspectNames * association * isLink * isFavorite * isLocked * path * properties * permissions  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/deleted-nodes")
    suspend fun listDeletedNodes(
        @retrofit2.http.Query("skipCount") skipCount: Int?,
        @retrofit2.http.Query("maxItems") maxItems: Int?,
        @retrofit2.http.Query("include") @CSV include: List<String>?
    ): DeletedNodesPaging
    /**
     * Restore a deleted node
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Attempts to restore the deleted node **nodeId** to its original location or to a new location.  If the node is successfully restored to its former primary parent, then only the  primary child association will be restored, including recursively for any primary  children. It should be noted that no other secondary child associations or peer  associations will be restored, for any of the nodes within the primary parent-child  hierarchy of restored nodes, irrespective of whether these associations were to  nodes within or outside of the restored hierarchy.   Also, any previously shared link will not be restored since it is deleted at the time  of delete of each node. 
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     * @param deletedNodeBodyRestore The targetParentId if the node is restored to a new location. (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/deleted-nodes/{nodeId}/restore")
    suspend fun restoreDeletedNode(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?,
        @retrofit2.http.Body deletedNodeBodyRestore: DeletedNodeBodyRestore
    ): NodeEntry
}

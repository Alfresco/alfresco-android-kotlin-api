/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.Error
import com.alfresco.content.models.RatingBody
import com.alfresco.content.models.RatingEntry
import com.alfresco.content.models.RatingPaging
import com.alfresco.content.tools.CSV
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface RatingsApi {
    /**
     * Create a rating
     * Create a rating for the node with identifier **nodeId**
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param ratingBodyCreate For \&quot;myRating\&quot; the type is specific to the rating scheme, boolean for the likes and an integer for the fiveStar.  For example, to \&quot;like\&quot; a file the following body would be used:  &#x60;&#x60;&#x60;JSON   {     \&quot;id\&quot;: \&quot;likes\&quot;,     \&quot;myRating\&quot;: true   } &#x60;&#x60;&#x60;  (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/nodes/{nodeId}/ratings")
    suspend fun createRating(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Body ratingBodyCreate: RatingBody,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): RatingEntry
    /**
     * Delete a rating
     * Deletes rating **ratingId** from node **nodeId**.
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param ratingId The identifier of a rating. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("alfresco/versions/1/nodes/{nodeId}/ratings/{ratingId}")
    suspend fun deleteRating(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Path("ratingId") ratingId: String
    ): Unit
    /**
     * Get a rating
     * Get the specific rating **ratingId** on node **nodeId**.
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param ratingId The identifier of a rating. (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/nodes/{nodeId}/ratings/{ratingId}")
    suspend fun getRating(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Path("ratingId") ratingId: String,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): RatingEntry
    /**
     * List ratings
     * Gets a list of ratings for node **nodeId**.
     * The endpoint is owned by defaultname service owner
     * @param nodeId The identifier of a node. (required)
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/nodes/{nodeId}/ratings")
    suspend fun listRatings(
        @retrofit2.http.Path("nodeId") nodeId: String,
        @retrofit2.http.Query("skipCount") skipCount: Int?,
        @retrofit2.http.Query("maxItems") maxItems: Int?,
        @retrofit2.http.Query("fields") @CSV fields: List<String>?
    ): RatingPaging
}

/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.Error
import com.alfresco.content.models.NodePaging
import com.alfresco.content.models.PersonPaging
import com.alfresco.content.models.SitePaging
import com.alfresco.content.tools.CSV
import retrofit2.http.GET
import retrofit2.http.Headers

@JvmSuppressWildcards
interface QueriesApi {
    /**
     * Find nodes
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of nodes that match the given search criteria.  The search term is used to look for nodes that match against name, title, description, full text content or tags.  The search term: - must contain a minimum of 3 alphanumeric characters - allows \"quoted term\" - can optionally use '*' for wildcard matching  By default, file and folder types will be searched unless a specific type is provided as a query parameter.  By default, the search will be across the repository unless a specific root node id is provided to start the search from.  You can sort the result list using the **orderBy** parameter. You can specify one or more of the following fields in the **orderBy** parameter: * name * modifiedAt * createdAt 
     * The endpoint is owned by defaultname service owner
     * @param term The term to search for. (required)
     * @param rootNodeId The id of the node to start the search from.  Supports the aliases -my-, -root- and -shared-.  (optional)
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param nodeType Restrict the returned results to only those of the given node type and its sub-types  (optional)
     * @param include Returns additional information about the node. The following optional fields can be requested: * allowableOperations * aspectNames * isLink * isFavorite * isLocked * path * properties  (optional)
     * @param orderBy A string to control the order of the entities returned in a list. You can use the **orderBy** parameter to sort the list by one or more fields.  Each field has a default sort order, which is normally ascending order. Read the API method implementation notes above to check if any fields used in this method have a descending default search order.  To sort the entities in a specific order, you can use the **ASC** and **DESC** keywords for any field.  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/queries/nodes")
    suspend fun findNodes(
        @retrofit2.http.Query("term") term: String,
        @retrofit2.http.Query("rootNodeId") rootNodeId: String? = null,
        @retrofit2.http.Query("skipCount") skipCount: Int? = null,
        @retrofit2.http.Query("maxItems") maxItems: Int? = null,
        @retrofit2.http.Query("nodeType") nodeType: String? = null,
        @retrofit2.http.Query("include") @CSV include: List<String>? = null,
        @retrofit2.http.Query("orderBy") @CSV orderBy: List<String>? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): NodePaging
    /**
     * Find people
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of people that match the given search criteria.  The search term is used to look for matches against person id, firstname and lastname.  The search term: - must contain a minimum of 2 alphanumeric characters - can optionally use '*' for wildcard matching within the term  You can sort the result list using the **orderBy** parameter. You can specify one or more of the following fields in the **orderBy** parameter: * id * firstName * lastName        
     * The endpoint is owned by defaultname service owner
     * @param term The term to search for.  (required)
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     * @param orderBy A string to control the order of the entities returned in a list. You can use the **orderBy** parameter to sort the list by one or more fields.  Each field has a default sort order, which is normally ascending order. Read the API method implementation notes above to check if any fields used in this method have a descending default search order.  To sort the entities in a specific order, you can use the **ASC** and **DESC** keywords for any field.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/queries/people")
    suspend fun findPeople(
        @retrofit2.http.Query("term") term: String,
        @retrofit2.http.Query("skipCount") skipCount: Int? = null,
        @retrofit2.http.Query("maxItems") maxItems: Int? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null,
        @retrofit2.http.Query("orderBy") @CSV orderBy: List<String>? = null
    ): PersonPaging
    /**
     * Find sites
     * **Note:** this endpoint is available in Alfresco 5.2 and newer versions.  Gets a list of sites that match the given search criteria.  The search term is used to look for sites that match against site id, title or description.  The search term: - must contain a minimum of 2 alphanumeric characters - can optionally use '*' for wildcard matching within the term  The default sort order for the returned list is for sites to be sorted by ascending id.  You can override the default by using the **orderBy** parameter. You can specify one or more of the following fields in the **orderBy** parameter: * id * title * description 
     * The endpoint is owned by defaultname service owner
     * @param term The term to search for. (required)
     * @param skipCount The number of entities that exist in the collection before those included in this list.  If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list.  If not supplied then the default value is 100.  (optional, default to 100)
     * @param orderBy A string to control the order of the entities returned in a list. You can use the **orderBy** parameter to sort the list by one or more fields.  Each field has a default sort order, which is normally ascending order. Read the API method implementation notes above to check if any fields used in this method have a descending default search order.  To sort the entities in a specific order, you can use the **ASC** and **DESC** keywords for any field.  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/queries/sites")
    suspend fun findSites(
        @retrofit2.http.Query("term") term: String,
        @retrofit2.http.Query("skipCount") skipCount: Int? = null,
        @retrofit2.http.Query("maxItems") maxItems: Int? = null,
        @retrofit2.http.Query("orderBy") @CSV orderBy: List<String>? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): SitePaging
}

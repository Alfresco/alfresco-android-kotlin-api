/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Alfresco Content Services REST API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.alfresco.content.apis

import com.alfresco.content.models.Error
import com.alfresco.content.models.FavoriteBodyCreate
import com.alfresco.content.models.FavoriteEntry
import com.alfresco.content.models.FavoritePaging
import com.alfresco.content.models.FavoriteSiteBodyCreate
import com.alfresco.content.models.FavoriteSiteEntry
import com.alfresco.content.models.SiteEntry
import com.alfresco.content.models.SitePaging
import com.alfresco.content.tools.CSV
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

@JvmSuppressWildcards
interface FavoritesApi {
    /**
     * Create a favorite
     * Favorite a **site**, **file**, or **folder** in the repository.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user.  **Note:** You can favorite more than one entity by specifying a list of objects in the JSON body like this:  ```JSON [   {        \"target\": {           \"file\": {              \"guid\": \"abcde-01234-....\"           }        }    },    {        \"target\": {           \"file\": {              \"guid\": \"abcde-09863-....\"           }        }    }, ] ``` If you specify a list as input, then a paginated list rather than an entry is returned in the response body. For example:  ```JSON {   \"list\": {     \"pagination\": {       \"count\": 2,       \"hasMoreItems\": false,       \"totalItems\": 2,       \"skipCount\": 0,       \"maxItems\": 100     },     \"entries\": [       {         \"entry\": {           ...         }       },       {         \"entry\": {           ...         }       }     ]   } } ``` 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param favoriteBodyCreate An object identifying the entity to be favorited.  The object consists of a single property which is an object with the name &#x60;site&#x60;, &#x60;file&#x60;, or &#x60;folder&#x60;. The content of that object is the &#x60;guid&#x60; of the target entity.  For example, to favorite a file the following body would be used:  &#x60;&#x60;&#x60;JSON {    \&quot;target\&quot;: {       \&quot;file\&quot;: {          \&quot;guid\&quot;: \&quot;abcde-01234-....\&quot;       }    } } &#x60;&#x60;&#x60;  (required)
     * @param include Returns additional information about favorites, the following optional fields can be requested: * path (note, this only applies to files and folders) * properties  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/people/{personId}/favorites")
    suspend fun createFavorite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Body favoriteBodyCreate: FavoriteBodyCreate,
        @retrofit2.http.Query("include") @CSV include: List<String>? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): FavoriteEntry
    /**
     * Create a site favorite
     * **Note:** this endpoint is deprecated as of Alfresco 4.2, and will be removed in the future. Use `/people/{personId}/favorites` instead.  Create a site favorite for person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user.   **Note:** You can favorite more than one site by specifying a list of sites in the JSON body like this:  ```JSON [   {     \"id\": \"test-site-1\"   },   {     \"id\": \"test-site-2\"   } ] ``` If you specify a list as input, then a paginated list rather than an entry is returned in the response body. For example:  ```JSON {   \"list\": {     \"pagination\": {       \"count\": 2,       \"hasMoreItems\": false,       \"totalItems\": 2,       \"skipCount\": 0,       \"maxItems\": 100     },     \"entries\": [       {         \"entry\": {           ...         }       },       {         \"entry\": {           ...         }       }     ]   } } ``` 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param favoriteSiteBodyCreate The id of the site to favorite. (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @POST("alfresco/versions/1/people/{personId}/favorite-sites")
    @Deprecated(message = "Deprecated")
    suspend fun createSiteFavorite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Body favoriteSiteBodyCreate: FavoriteSiteBodyCreate,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): FavoriteSiteEntry
    /**
     * Delete a favorite
     * Deletes **favoriteId** as a favorite of person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user. 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param favoriteId The identifier of a favorite. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("alfresco/versions/1/people/{personId}/favorites/{favoriteId}")
    suspend fun deleteFavorite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Path("favoriteId") favoriteId: String
    ): Unit
    /**
     * Delete a site favorite
     * **Note:** this endpoint is deprecated as of Alfresco 4.2, and will be removed in the future. Use `/people/{personId}/favorites/{favoriteId}` instead.  Deletes site **siteId** from the favorite site list of person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user. 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param siteId The identifier of a site. (required)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @DELETE("alfresco/versions/1/people/{personId}/favorite-sites/{siteId}")
    @Deprecated(message = "Deprecated")
    suspend fun deleteSiteFavorite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Path("siteId") siteId: String
    ): Unit
    /**
     * Get a favorite
     * Gets favorite **favoriteId** for person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user. 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param favoriteId The identifier of a favorite. (required)
     * @param include Returns additional information about favorites, the following optional fields can be requested: * path (note, this only applies to files and folders) * properties  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/people/{personId}/favorites/{favoriteId}")
    suspend fun getFavorite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Path("favoriteId") favoriteId: String,
        @retrofit2.http.Query("include") @CSV include: List<String>? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): FavoriteEntry
    /**
     * Get a favorite site
     * **Note:** this endpoint is deprecated as of Alfresco 4.2, and will be removed in the future. Use `/people/{personId}/favorites/{favoriteId}` instead.  Gets information on favorite site **siteId** of person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user. 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param siteId The identifier of a site. (required)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/people/{personId}/favorite-sites/{siteId}")
    @Deprecated(message = "Deprecated")
    suspend fun getFavoriteSite(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Path("siteId") siteId: String,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): SiteEntry
    /**
     * List favorite sites
     * **Note:** this endpoint is deprecated as of Alfresco 4.2, and will be removed in the future. Use `/people/{personId}/favorites` instead.  Gets a list of a person's favorite sites.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user. 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param skipCount The number of entities that exist in the collection before those included in this list. If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list. If not supplied then the default value is 100.  (optional, default to 100)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/people/{personId}/favorite-sites")
    @Deprecated(message = "Deprecated")
    suspend fun listFavoriteSitesForPerson(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Query("skipCount") skipCount: Int? = null,
        @retrofit2.http.Query("maxItems") maxItems: Int? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): SitePaging
    /**
     * List favorites
     * Gets a list of favorites for person **personId**.  You can use the `-me-` string in place of `<personId>` to specify the currently authenticated user.  The default sort order for the returned list of favorites is type ascending, createdAt descending. You can override the default by using the **orderBy** parameter.  You can use any of the following fields to order the results: *   `type` *   `createdAt` *   `title`  You can use the **where** parameter to restrict the list in the response to entries of a specific kind. The **where** parameter takes a value. The value is a single predicate that can include one or more **EXISTS** conditions. The **EXISTS** condition uses a single operand to limit the list to include entries that include that one property. The property values are:  *   `target/file` *   `target/folder` *   `target/site`  For example, the following **where** parameter restricts the returned list to the file favorites for a person:  ```SQL (EXISTS(target/file)) ``` You can specify more than one condition using **OR**. The predicate must be enclosed in parentheses.   For example, the following **where** parameter restricts the returned list to the file and folder favorites for a person:  ```SQL (EXISTS(target/file) OR EXISTS(target/folder)) ``` 
     * The endpoint is owned by defaultname service owner
     * @param personId The identifier of a person. (required)
     * @param skipCount The number of entities that exist in the collection before those included in this list. If not supplied then the default value is 0.  (optional, default to 0)
     * @param maxItems The maximum number of items to return in the list. If not supplied then the default value is 100.  (optional, default to 100)
     * @param orderBy A string to control the order of the entities returned in a list. You can use the **orderBy** parameter to sort the list by one or more fields.  Each field has a default sort order, which is normally ascending order. Read the API method implementation notes above to check if any fields used in this method have a descending default search order.  To sort the entities in a specific order, you can use the **ASC** and **DESC** keywords for any field.  (optional)
     * @param where A string to restrict the returned objects by using a predicate. (optional)
     * @param include Returns additional information about favorites, the following optional fields can be requested: * path (note, this only applies to files and folders) * properties  (optional)
     * @param fields A list of field names.  You can use this parameter to restrict the fields returned within a response if, for example, you want to save on overall bandwidth.  The list applies to a returned individual entity or entries within a collection.  If the API method also supports the **include** parameter, then the fields specified in the **include** parameter are returned in addition to those specified in the **fields** parameter.  (optional)
     */
    @Headers(
        "Content-Type: application/json"
    )
    @GET("alfresco/versions/1/people/{personId}/favorites")
    suspend fun listFavorites(
        @retrofit2.http.Path("personId") personId: String,
        @retrofit2.http.Query("skipCount") skipCount: Int? = null,
        @retrofit2.http.Query("maxItems") maxItems: Int? = null,
        @retrofit2.http.Query("orderBy") @CSV orderBy: List<String>? = null,
        @retrofit2.http.Query("where") where: String? = null,
        @retrofit2.http.Query("include") @CSV include: List<String>? = null,
        @retrofit2.http.Query("fields") @CSV fields: List<String>? = null
    ): FavoritePaging
}

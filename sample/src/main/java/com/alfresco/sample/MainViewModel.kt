package com.alfresco.sample

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthInterceptor
import com.alfresco.auth.data.MutableLiveEvent
import com.alfresco.content.apis.AdvanceSearchInclude
import com.alfresco.content.apis.AlfrescoApi
import com.alfresco.content.apis.FacetSearchInclude
import com.alfresco.content.apis.FavoritesApi
import com.alfresco.content.apis.NodesApi
import com.alfresco.content.apis.SearchApi
import com.alfresco.content.apis.TrashcanApi
import com.alfresco.content.apis.advanceSearch
import com.alfresco.content.models.AppConfigModel
import com.alfresco.content.models.RequestDefaults
import com.alfresco.content.models.RequestFacetField
import com.alfresco.content.models.RequestFacetFields
import com.alfresco.content.models.RequestFacetIntervals
import com.alfresco.content.models.RequestFacetIntervalsInIntervals
import com.alfresco.content.models.RequestFacetQueriesInner
import com.alfresco.content.models.RequestFacetSet
import com.alfresco.content.models.RequestFilterQueriesInner
import com.alfresco.content.models.RequestIncludeEnum
import com.alfresco.content.models.RequestPagination
import com.alfresco.content.models.RequestQuery
import com.alfresco.content.models.RequestSortDefinitionInner
import com.alfresco.content.models.RequestTemplatesInner
import com.alfresco.content.models.ResultNode
import com.alfresco.content.models.SearchRequest
import com.alfresco.content.tools.GeneratedCodeConverters
import com.alfresco.process.apis.ProcessAPI
import com.alfresco.process.apis.TaskAPI
import com.alfresco.process.models.RequestProcessInstances
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class MainViewModel(private val context: Context) : ViewModel() {

    private val retrofit: Retrofit
    private val retrofitConfig: Retrofit
    private val retrofitAPS: Retrofit
    private val authInterceptor: AuthInterceptor
    private val loggingInterceptor: HttpLoggingInterceptor

    val results = MutableLiveData<List<ResultNode>>()
    val resultsConfig = MutableLiveData<AppConfigModel?>()
    val onError = MutableLiveEvent<String>()
    val onSessionExpired = MutableLiveEvent<Boolean>()

    init {
        val account = requireNotNull(Account.getAccount(context))

        authInterceptor = AuthInterceptor(
            context,
            account.username,
            account.authType,
            account.authState,
            AuthConfig.defaultConfig.jsonSerialize()
        )

        authInterceptor.setListener(object : AuthInterceptor.Listener {
            override fun onAuthStateChange(accountId: String, authState: String) {
                Account.update(context, authState)
            }

            override fun onAuthFailure(accountId: String, url: String) {
                onSessionExpired.postValue(true)
            }
        })

        loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val baseUrl = "${account.serverUrl}/api/-default-/public/"
        val baseUrlAPS = account.serverUrl.replace("/alfresco", "/activiti-app/")
        val okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GeneratedCodeConverters.converterFactory())
            .baseUrl(baseUrl)
            .build()

        retrofitAPS = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GeneratedCodeConverters.converterFactory())
            .baseUrl(baseUrlAPS)
            .build()
        retrofitConfig = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GeneratedCodeConverters.converterFactory())
            .baseUrl("https://mobileapps.envalfresco.com/adf/")
            .build()
    }

    fun loadRecents() {
        val service = retrofit.create(SearchApi::class.java)
        val trashApi = retrofit.create(TrashcanApi::class.java)
        val favoritesApi = retrofit.create(FavoritesApi::class.java)
        val serviceApi1 = retrofit.create(NodesApi::class.java)
        val serviceAPS = retrofitAPS.create(TaskAPI::class.java)
        val serviceAPS1 = retrofitAPS.create(ProcessAPI::class.java)

        val templates =
            listOf(
                RequestTemplatesInner(
                    "keywords",
                    "%(cm:name cm:title cm:description TEXT TAG)"
                )
            )

        val defaults = RequestDefaults(
            defaultFieldName = "keywords",
            defaultFTSOperator = RequestDefaults.DefaultFTSOperatorEnum.AND
        )

        val reqInclude = listOf(RequestIncludeEnum.PATH)

        val queryString = "file"
//        val queryString = "((cm:name:\"istock*\" OR cm:title:\"istock*\" OR cm:description:\"istock*\" OR TEXT:\"istock*\" OR TAG:\"istock*\" OR schema:textLines:\"istock*\" OR schema:transcription:\"istock*\" OR schema:label:\"istock*\" OR schema:product:\"istock*\" OR schema:date:\"istock*\" OR schema:place:\"istock*\" OR schema:event:\"istock*\" OR schema:organization:\"istock*\" OR schema:thing:\"istock*\" OR schema:quantity:\"istock*\" OR schema:creativeWork:\"istock*\" OR schema:piiEntityTypes:\"istock*\" OR schema:person:\"istock*\"))"
        val reqQuery = RequestQuery(queryString, RequestQuery.LanguageEnum.AFTS)
        val filter = listOf(
            RequestFilterQueriesInner("(TYPE:'cm:folder' OR TYPE:'cm:content') AND (NOT cm:creator:System)"),
            RequestFilterQueriesInner("(TYPE:'cm:folder') AND (NOT cm:creator:System)"),
            RequestFilterQueriesInner("-TYPE:'st:site'"),
            RequestFilterQueriesInner("-TYPE:'cm:thumbnail' AND -TYPE:'cm:failedThumbnail' AND -TYPE:'cm:rating'"),
            RequestFilterQueriesInner("-ASPECT:'st:siteContainer' AND -ASPECT:'sys:hidden'"),
            RequestFilterQueriesInner("-TYPE:'dl:dataList' AND -TYPE:'dl:todoList'"),
            RequestFilterQueriesInner("-TYPE:'dl:issue' AND -TYPE:'dl:task' AND -TYPE:'dl:simpletask'"),
            RequestFilterQueriesInner("-TYPE:'dl:event' AND -TYPE:'dl:eventAgenda' AND -TYPE:'dl:meetingAgenda'"),
            RequestFilterQueriesInner("-TYPE:'dl:contact' AND -TYPE:'dl:location'"),
            RequestFilterQueriesInner("-TYPE:'fm:forum' AND -TYPE:'fm:topic' AND -TYPE:'fm:post'"),
            RequestFilterQueriesInner("-TYPE:'app:filelink' AND -TYPE:'lnk:link' AND -TYPE:'ia:calendarEvent'"),
            RequestFilterQueriesInner("-QNAME:comment AND -PNAME:'0/wiki'")
        )

        val facetFields = listOf(
            RequestFacetField(
                field = "content.mimetype",
                label = "SEARCH.FACET_FIELDS.TYPE",
                mincount = 1
            ),
            RequestFacetField(
                field = "content.size",
                label = "SEARCH.FACET_FIELDS.SIZE",
                mincount = 1
            ),
            RequestFacetField(
                field = "creator",
                label = "SEARCH.FACET_FIELDS.CREATOR",
                mincount = 1
            ),
            RequestFacetField(
                field = "modifier",
                label = "SEARCH.FACET_FIELDS.MODIFIER",
                mincount = 1
            ),
            RequestFacetField(
                field = "created",
                label = "SEARCH.FACET_FIELDS.CREATED",
                mincount = 1
            )
        )

        val facetQueries = listOf(
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.CREATED_THIS_YEAR",
                query = "created:2019",
                group = "SEARCH.FACET_QUERIES.MY_FACET_QUERIES"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.MIMETYPE",
                query = "content.mimetype:text/html",
                group = "Type facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.XTRASMALL",
                query = "content.size:[0 TO 10240]",
                group = "Size facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.SMALL",
                query = "content.size:[10240 TO 102400]",
                group = "Size facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.MEDIUM",
                query = "content.size:[102400 TO 1048576]",
                group = "Size facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.LARGE",
                query = "content.size:[1048576 TO 16777216]",
                group = "Size facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.XTRALARGE",
                query = "content.size:[16777216 TO 134217728]",
                group = "Size facet queries"
            ),
            RequestFacetQueriesInner(
                label = "SEARCH.FACET_QUERIES.XXTRALARGE",
                query = "content.size:[134217728 TO MAX]",
                group = "Size facet queries"
            )
        )

        val facetInterval = listOf(
            RequestFacetIntervalsInIntervals(
                label = "The.Created", field = "cm:created", sets = listOf(
                    RequestFacetSet(
                        label = "lastYear",
                        start = "2020",
                        end = "2021",
                        endInclusive = false
                    ),
                    RequestFacetSet(
                        label = "currentYear",
                        start = "NOW/YEAR",
                        end = "NOW/YEAR+1YEAR"
                    ),
                    RequestFacetSet(
                        label = "earlier",
                        start = "*",
                        end = "2021",
                        endInclusive = false
                    )
                )
            ), RequestFacetIntervalsInIntervals(
                label = "TheModified", field = "cm:modified", sets = listOf(
                    RequestFacetSet(
                        label = "2017",
                        start = "2017",
                        end = "2018",
                        endInclusive = false
                    ),
                    RequestFacetSet(
                        label = "2017-2018",
                        start = "2017",
                        end = "2018",
                        endInclusive = true
                    ),
                    RequestFacetSet(
                        label = "currentYear",
                        start = "NOW/YEAR",
                        end = "NOW/YEAR+1YEAR"
                    ),
                    RequestFacetSet(
                        label = "earlierThan2017",
                        start = "*",
                        end = "2017",
                        endInclusive = false
                    )
                )
            )
        )

        val include = listOf(RequestIncludeEnum.PATH)
        val sort = listOf(
            RequestSortDefinitionInner(
                RequestSortDefinitionInner.TypeEnum.FIELD,
                "score",
                false
            )
        )
//        val sort = listOf(RequestSortDefinitionInner(RequestSortDefinitionInner.TypeEnum.FIELD, "cm:modified", false))
        val pagination = RequestPagination(25, 0)
        val search = SearchRequest(
            reqQuery,
            sort = sort,
            filterQueries = filter,
            include = include,
            paging = pagination,
            facetFields = RequestFacetFields(facetFields),
            facetQueries = facetQueries,
            defaults = defaults,
            templates = templates,
            facetIntervals = RequestFacetIntervals(intervals = facetInterval),
            facetFormat = "V2"
        )

        viewModelScope.launch {
            try {

                val searchCall = service.advanceSearch(
                    "file",
                    null,
                    skipCount = 0,
                    maxItems = 25,
                    setOf(
                        AdvanceSearchInclude(
                            query = "TYPE:'cm:folder' OR TYPE:'cm:content'",
                            name = "TYPE:'cm:folder' OR TYPE:'cm:content'"
                        ), AdvanceSearchInclude(
                            query = "NOT cm:creator:System",
                            name = "NOT cm:creator:System"
                        )
                    ),
                    FacetSearchInclude(
                        fields = facetFields,
                        queries = facetQueries,
                        intervals = facetInterval
                    ),
                    facetFormat = "V2"
                )

                /*
                *  val where = "(EXISTS(target/file) OR EXISTS(target/folder))"
        val include = AlfrescoApi.csvQueryParam("path", "allowableOperations")
        val orderBy = listOf("title ASC")
                *
                */

                val where = "(EXISTS(target/file) OR EXISTS(target/folder))"
                val include = AlfrescoApi.csvQueryParam("path", "allowableOperations")
                val orderBy = listOf("title ASC")

                val responseFavorite = favoritesApi.listFavorites(
                    AlfrescoApi.CURRENT_USER,
                    0,
                    25,
                    orderBy,
                    where,
                    include,
                    null,
                )

//                val taskList = serviceAPS1.startForm("Testprocessaman:2:51")
                val taskList = serviceAPS1.createProcessInstance(
                    RequestProcessInstances(
                        name = "Test process aman",
                        processDefinitionId = "Testprocessaman:2:51",
                        values = mapOf(Pair("firstname", "Amanpal"), Pair("lastname", "Singh"))

                    )
                )


                /*{"name":"Test process aman - Mar 15, 2024, 8:15:45 PM","processDefinitionId":"Testprocessaman:2:51","values":{"firstname":"Amanpal","lastname":"Singh"}}*/
//                println("data task 11 ==> ${taskList.fields?.first()?.getFieldMapAsList()}")

                /*val list = searchCall.list?.entries?.map { it.entry } ?: emptyList()
                results.value = list*/

                println("Response start workflow = $taskList")

                /*val response = serviceApi1.deleteNode("ea7ccff4-3b8c-48f7-ae75-3b5fbba1e00d")

                println("MainViewModel.loadRecents delete ${response.code()}")*/

                val queries = searchCall.list?.context?.facetQueries
            } catch (ex: Exception) {
                ex.printStackTrace()
                onError.value = ex.localizedMessage ?: ""
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

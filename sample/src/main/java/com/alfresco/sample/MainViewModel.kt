package com.alfresco.sample

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthInterceptor
import com.alfresco.auth.data.MutableLiveEvent
import com.alfresco.content.apis.AppConfigApi
import com.alfresco.content.apis.SearchApi
import com.alfresco.content.models.AppConfigModel
import com.alfresco.content.models.RequestFacetField
import com.alfresco.content.models.RequestFacetFields
import com.alfresco.content.models.RequestFacetIntervals
import com.alfresco.content.models.RequestFacetIntervalsIntervals
import com.alfresco.content.models.RequestFacetQueriesInner
import com.alfresco.content.models.RequestFacetSet
import com.alfresco.content.models.RequestFilterQueriesInner
import com.alfresco.content.models.RequestIncludeEnum
import com.alfresco.content.models.RequestPagination
import com.alfresco.content.models.RequestQuery
import com.alfresco.content.models.RequestSortDefinitionInner
import com.alfresco.content.models.ResultNode
import com.alfresco.content.models.SearchRequest
import com.alfresco.content.tools.GeneratedCodeConverters
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class MainViewModel(private val context: Context) : ViewModel() {

    private val retrofit: Retrofit
    private val retrofitConfig: Retrofit
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

            override fun onAuthFailure(accountId: String) {
                onSessionExpired.postValue(true)
            }
        })

        loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val baseUrl = "${account.serverUrl}/api/-default-/public/"
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
        retrofitConfig = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GeneratedCodeConverters.converterFactory())
            .baseUrl("https://mobileapps.envalfresco.com/adf/")
            .build()
    }

    fun loadRecents() {
        val service = retrofit.create(SearchApi::class.java)
        val queryString = "*"
        val reqQuery = RequestQuery(queryString, RequestQuery.LanguageEnum.AFTS)
        val filter = listOf(
            RequestFilterQueriesInner("cm:modified:[NOW/DAY-30DAYS TO NOW/DAY+1DAY]"),
            RequestFilterQueriesInner("TYPE:\"content\"")
        )
        val facetFields = listOf(
            RequestFacetField(field = "content.mimetype", label = "SEARCH.FACET_FIELDS.TYPE", mincount = 1),
            RequestFacetField(field = "content.size", label = "SEARCH.FACET_FIELDS.SIZE", mincount = 1),
            RequestFacetField(field = "creator", label = "SEARCH.FACET_FIELDS.CREATOR", mincount = 1),
            RequestFacetField(field = "modifier", label = "SEARCH.FACET_FIELDS.MODIFIER", mincount = 1),
            RequestFacetField(field = "created", label = "SEARCH.FACET_FIELDS.CREATED", mincount = 1)
        )

        val facetQueries = listOf(
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.CREATED_THIS_YEAR", query = "created:2019"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.MIMETYPE", query = "content.mimetype:text/html"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.XTRASMALL", query = "content.size:[0 TO 10240]"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.SMALL", query = "content.size:[10240 TO 102400]"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.MEDIUM", query = "content.size:[102400 TO 1048576]"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.LARGE", query = "content.size:[1048576 TO 16777216]"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.XTRALARGE", query = "content.size:[16777216 TO 134217728]"),
            RequestFacetQueriesInner(label = "SEARCH.FACET_QUERIES.XXTRALARGE", query = "content.size:[134217728 TO MAX]")
        )

        val facetInterval = listOf(
            RequestFacetIntervalsIntervals(
                label = "The Created", field = "cm:created", sets = listOf(
                    RequestFacetSet(label = "lastYear", start = "2018", end = "2019", endInclusive = false),
                    RequestFacetSet(label = "currentYear", start = "NOW/YEAR", end = "NOW/YEAR+1YEAR"),
                    RequestFacetSet(label = "earlier", start = "*", end = "2018", endInclusive = false)
                )
            ), RequestFacetIntervalsIntervals(
                label = "TheModified", field = "cm:modified", sets = listOf(
                    RequestFacetSet(label = "2017", start = "2017", end = "2018", endInclusive = false),
                    RequestFacetSet(label = "2017-2018", start = "2017", end = "2018", endInclusive = true),
                    RequestFacetSet(label = "currentYear", start = "NOW/YEAR", end = "NOW/YEAR+1YEAR"),
                    RequestFacetSet(label = "earlierThan2017", start = "*", end = "2017", endInclusive = false)
                )
            )
        )

        val include = listOf(RequestIncludeEnum.PATH)
        val sort = listOf(RequestSortDefinitionInner(RequestSortDefinitionInner.TypeEnum.FIELD, "cm:modified", false))
        val pagination = RequestPagination(25, 0)
        val search = SearchRequest(
            reqQuery, sort = sort, filterQueries = filter, include = include, paging = pagination,
            facetFields = RequestFacetFields(facetFields), facetQueries = facetQueries, facetIntervals = RequestFacetIntervals(intervals = facetInterval)
        )

        viewModelScope.launch {
            try {
                results.value = service.search(search).list?.entries?.map { it.entry } ?: emptyList()
            } catch (ex: Exception) {
                onError.value = ex.localizedMessage ?: ""
            }
        }
    }

    fun loadAppConfig() {
        val service = retrofitConfig.create(AppConfigApi::class.java)
        viewModelScope.launch {
            try {
                resultsConfig.value = service.getAppConfig()
            } catch (ex: Exception) {
                onError.value = ex.localizedMessage ?: ""
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

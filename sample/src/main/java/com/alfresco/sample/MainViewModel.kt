package com.alfresco.sample

import android.content.Context
import androidx.lifecycle.*
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthInterceptor
import com.alfresco.auth.data.MutableLiveEvent
import com.alfresco.content.apis.SearchApi
import com.alfresco.content.models.*
import com.alfresco.content.tools.GeneratedCodeConverters
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class MainViewModel(private val context: Context): ViewModel() {

    private val retrofit: Retrofit
    private val authInterceptor: AuthInterceptor
    private val loggingInterceptor: HttpLoggingInterceptor

    val results = MutableLiveData<List<ResultNode>>()
    val onError = MutableLiveEvent<String>()

    init {
        val account = requireNotNull(Account.getAccount(context))

        authInterceptor = AuthInterceptor (
            context,
            account.username,
            account.authType,
            account.authState,
            AuthConfig.defaultConfig.jsonSerialize()
        )

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
    }

    fun loadRecents() {
        val service = retrofit.create(SearchApi::class.java)
        val queryString = "*"
        val reqQuery = RequestQuery(queryString, RequestQuery.LanguageEnum.AFTS)
        val filter = listOf(
            RequestFilterQueriesInner("cm:modified:[NOW/DAY-30DAYS TO NOW/DAY+1DAY]"),
            RequestFilterQueriesInner("TYPE:\"content\"")
        )
        val include = listOf(RequestIncludeEnum.PATH)
        val sort = listOf(RequestSortDefinitionInner(RequestSortDefinitionInner.TypeEnum.FIELD, "cm:modified", false))
        val pagination = RequestPagination(25, 0)
        val search = SearchRequest(reqQuery, sort = sort, filterQueries = filter, include = include, paging = pagination)

        viewModelScope.launch {
            try {
                results.value = service.search(search).list?.entries?.map { it.entry } ?: emptyList()
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

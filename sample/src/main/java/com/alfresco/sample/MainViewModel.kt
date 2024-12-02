package com.alfresco.sample

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alfresco.auth.AuthConfig
import com.alfresco.auth.AuthInterceptor
import com.alfresco.auth.data.MutableLiveEvent
import com.alfresco.content.apis.FavoritesApi
import com.alfresco.content.apis.NodesApi
import com.alfresco.content.apis.SearchApi
import com.alfresco.content.apis.TrashcanApi
import com.alfresco.content.apis.recentFiles
import com.alfresco.content.models.AppConfigModel
import com.alfresco.content.models.ResultNode
import com.alfresco.content.tools.GeneratedCodeConverters
import com.alfresco.process.apis.ProcessAPI
import com.alfresco.process.apis.TaskAPI
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
    private var account: Account = requireNotNull(Account.getAccount(context))

    init {

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



        viewModelScope.launch {
            try {

                val searchCall = service.recentFiles(
                    "demo",
                    30,
                    skipCount = 0,
                    maxItems = 25,
                )

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

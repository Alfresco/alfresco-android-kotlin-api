package com.alfresco.core.network.request

import android.util.Log
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Bogdan Roatis on 4/2/2019.
 */
class Request(path: String) {

    val url: URL?

    init {
        url = createUrl(path)
    }

    lateinit var method: Method
        private set

    var headers: MutableMap<String, String> = mutableMapOf()
        private set

    var params: MutableMap<String, String> = mutableMapOf()
        private set

    var body: String? = null
        private set

    fun addHeader(name: String, value: String): Request {
        headers[name] = value
        return this
    }

    fun addParams(name: String, value: String): Request {
        params[name] = value
        return this
    }

    fun body(body: String): Request {
        this.body = body
        return this
    }

    fun get(): Request {
        method = Method.GET
        return this
    }

    fun post(): Request {
        method = Method.POST
        return this
    }

    fun head(): Request {
        method = Method.HEAD
        return this
    }

    private fun createUrl(path: String) =
        try {
            URL(path)
        } catch (e: MalformedURLException) {
            Log.e("Alfresco", "Couldn't create the url->$path $e")
            null
        }
}

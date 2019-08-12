package com.alfresco.core.network

import com.alfresco.core.network.request.Request

/**
 * Entry access point for initiating a request from outside
 *
 * Created by Bogdan Roatis on 3/26/2019.
 */
object Alfresco {

    fun with(path: String): Request {
        return Request(path = path)
    }
}

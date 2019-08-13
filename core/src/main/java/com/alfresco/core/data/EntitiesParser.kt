package com.alfresco.core.data

import okhttp3.Response

/**
 * Common ground for parsing specific detail implementation
 * entities to business logic entities
 *
 * Created by Bogdan Roatis on 3/26/2019.
 */


/**
 * Extension function for parsing the
 * specific detail implementation [Response]
 * to a business logic [AlfrescoResponse] object
 */
fun Response.toAlfrescoResponse() =
        AlfrescoResponse(
                body = body?.string(),
                url = request.url.encodedPath,
                statusCode = code,
                responseMessage = message)

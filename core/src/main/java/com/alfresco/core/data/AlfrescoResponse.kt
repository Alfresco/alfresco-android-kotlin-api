package com.alfresco.core.data

/**
 * The default response for every request.
 *
 * Created by Bogdan Roatis on 3/26/2019.
 */
data class AlfrescoResponse(
        /**
         * The url that was used to get this response
         */
        val url: String,

        /**
         * The status code
         */
        val statusCode: Int = -1,

        /**
         * The response message associated with the status code
         */
        val responseMessage: String = "",

        /**
         * The body of the response.
         */
        val body: String?
) : BasicResponse()

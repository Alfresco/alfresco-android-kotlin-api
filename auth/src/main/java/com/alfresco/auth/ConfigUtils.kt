package com.alfresco.auth

import android.content.Context

const val mobileConfigFileName = "mobile-config.json"

fun loadJSONFromAssets(context: Context): String? {
    return try {
        val inputStream = context.assets.open(mobileConfigFileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

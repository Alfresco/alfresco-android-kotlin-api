package com.alfresco.auth

import android.content.Context

fun loadJSONFromAssets(context: Context): String? {
    return try {
        val inputStream = context.assets.open("mobile-config.json")
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

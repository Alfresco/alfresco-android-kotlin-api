package com.alfresco.content.config

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import java.util.concurrent.CancellationException

/**
 * DownloadConfigApi contains method to download app.config from server
 */
class DownloadConfigApi(private val context: Context) {


    /**
     * this method is responsible to download the app.config.json for the advance filters
     */
    suspend fun enqueueDownload() {

        val dm = ContextCompat.getSystemService(context, DownloadManager::class.java)
        val request = DownloadManager.Request(Uri.parse(APP_CONFIG_URL))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS, APP_CONFIG_NAME)

        dm?.enqueue(request) ?: throw CancellationException("Missing DownloadManager service.")
    }

    private companion object{
        const val APP_CONFIG_NAME="app.config.json"
        const val APP_CONFIG_URL="https://mobileapps.envalfresco.com/adf/app.config.json"
    }

}

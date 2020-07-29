package com.alfresco.sample

import android.content.Context
import androidx.preference.PreferenceManager
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.Serializable
import java.lang.Exception

@Serializable
data class Account(
    val username: String,
    val authState: String,
    val authType: String,
    val serverUrl: String
) {
    companion object {
        private const val ACCOUNT_KEY = "account"

        fun createAccount(
            context: Context,
            username: String,
            authState: String,
            authType: String,
            serverUrl: String
        ) {
            // DO NOT store account data in Shared Pref unless it's encrypted.
            // This is just an easy example on a possible data format.
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

            val acc = Account(
                username,
                authState,
                authType,
                serverUrl
            )

            val json = Json(JsonConfiguration.Stable)
            val jsonData = json.stringify(serializer(), acc)

            val editor = sharedPrefs.edit()
            editor.putString("account", jsonData)
            editor.apply()

        }

        fun delete(context: Context) {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPrefs.edit()
            editor.remove("account")
            editor.apply()
        }

        fun getAccount(context: Context): Account? {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val jsonData = sharedPrefs.getString(ACCOUNT_KEY, null)
            val json = Json(JsonConfiguration.Stable)

            return try {
                json.parse(serializer(), jsonData ?: "")
            } catch (ex: Exception) {
                null
            }
        }
    }
}

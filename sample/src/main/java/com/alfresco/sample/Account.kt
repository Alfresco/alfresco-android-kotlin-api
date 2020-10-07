package com.alfresco.sample

import android.content.Context
import androidx.preference.PreferenceManager
import java.lang.Exception
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

            val jsonData = Json.encodeToString(serializer(), acc)

            val editor = sharedPrefs.edit()
            editor.putString("account", jsonData)
            editor.apply()
        }

        fun update(context: Context, authState: String) {
            val account = requireNotNull(getAccount(context))
            val newAccount = account.copy(authState = authState)

            val jsonData = Json.encodeToString(serializer(), newAccount)

            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
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

            return try {
                Json.decodeFromString(serializer(), jsonData ?: "")
            } catch (ex: Exception) {
                null
            }
        }
    }
}

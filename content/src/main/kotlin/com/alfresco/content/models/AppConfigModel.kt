package com.alfresco.content.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Advance search model
 * @property search
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class AppConfigModel(
    @Json(name = "search") @field:Json(name = "search") val search: List<SearchItem>?
) : Parcelable

package com.android.cardinalhealthtask.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumItem(
    @Json(name = "Albums")
    val albums: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String
)

@JsonClass(generateAdapter = true)
data class Album(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    val userId: Int
)
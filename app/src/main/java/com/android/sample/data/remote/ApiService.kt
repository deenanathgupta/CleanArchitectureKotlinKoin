package com.android.sample.data.remote

import com.android.sample.domain.model.Album
import com.android.sample.domain.model.AlbumItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("/albums")
    suspend fun getAlbums(): List<Album>

    @GET("/albums/{id}/photos")
    suspend fun getPhotos(@Path("id") id: String?): List<AlbumItem>
}
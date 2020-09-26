package com.android.sample.data.repository

import com.android.sample.data.remote.ApiService
import com.android.sample.domain.model.Album
import com.android.sample.domain.model.AlbumItem
import com.android.sample.domain.repository.AlbumRepository

class AlbumRepositoryImp (private val apiService: ApiService) : AlbumRepository {

    override suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    override suspend fun getPhotos(id:String?): List<AlbumItem> {
        return apiService.getPhotos(id)
    }
}
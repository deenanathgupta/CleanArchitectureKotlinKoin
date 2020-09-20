package com.android.cardinalhealthtask.data.repository

import com.android.cardinalhealthtask.data.remote.ApiService
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.domain.model.AlbumItem
import com.android.cardinalhealthtask.domain.repository.AlbumRepository

class AlbumRepositoryImp (private val apiService: ApiService) : AlbumRepository {

    override suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    override suspend fun getPhotos(id:String?): List<AlbumItem> {
        return apiService.getPhotos(id)
    }
}
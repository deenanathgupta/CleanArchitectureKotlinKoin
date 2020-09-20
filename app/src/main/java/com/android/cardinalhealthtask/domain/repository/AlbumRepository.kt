package com.android.cardinalhealthtask.domain.repository

import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.domain.model.AlbumItem

interface AlbumRepository {
    suspend fun getAlbums():List<Album>

    suspend fun getPhotos(id:String?):List<AlbumItem>
}
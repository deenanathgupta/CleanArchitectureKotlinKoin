package com.android.sample.domain.repository

import com.android.sample.domain.model.Album
import com.android.sample.domain.model.AlbumItem

interface AlbumRepository {
    suspend fun getAlbums():List<Album>

    suspend fun getPhotos(id:String?):List<AlbumItem>
}
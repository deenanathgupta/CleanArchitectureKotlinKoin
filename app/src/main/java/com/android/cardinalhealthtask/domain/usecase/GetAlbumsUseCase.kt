package com.android.cardinalhealthtask.domain.usecase

import com.android.cardinalhealthtask.domain.exception.ApiErrorHandle
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.domain.repository.AlbumRepository
import com.android.cardinalhealthtask.domain.usecase.base.UseCase


class GetAlbumsUseCase constructor(
    private val albumsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<Album>, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): List<Album> {
        return albumsRepository.getAlbums()
    }

}
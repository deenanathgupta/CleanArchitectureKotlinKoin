package com.android.sample.domain.usecase

import com.android.sample.domain.exception.ApiErrorHandle
import com.android.sample.domain.model.Album
import com.android.sample.domain.repository.AlbumRepository
import com.android.sample.domain.usecase.base.UseCase


class GetAlbumsUseCase constructor(
    private val albumsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<Album>, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): List<Album> {
        return albumsRepository.getAlbums()
    }

}
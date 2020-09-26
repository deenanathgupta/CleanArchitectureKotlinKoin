package com.android.sample.domain.usecase

import com.android.sample.domain.exception.ApiErrorHandle
import com.android.sample.domain.model.AlbumItem
import com.android.sample.domain.repository.AlbumRepository
import com.android.sample.domain.usecase.base.UseCase


class GetPhotosUseCase constructor(
    private val albumsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<AlbumItem>, String>(apiErrorHandle) {

    override suspend fun run(params: String?): List<AlbumItem> {
        return albumsRepository.getPhotos(params)
    }

}
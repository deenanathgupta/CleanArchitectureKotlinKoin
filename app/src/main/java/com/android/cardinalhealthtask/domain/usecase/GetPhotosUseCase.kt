package com.android.cardinalhealthtask.domain.usecase

import com.android.cardinalhealthtask.domain.exception.ApiErrorHandle
import com.android.cardinalhealthtask.domain.model.AlbumItem
import com.android.cardinalhealthtask.domain.repository.AlbumRepository
import com.android.cardinalhealthtask.domain.usecase.base.UseCase


class GetPhotosUseCase constructor(
    private val albumsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
) : UseCase<List<AlbumItem>, String>(apiErrorHandle) {

    override suspend fun run(params: String?): List<AlbumItem> {
        return albumsRepository.getPhotos(params)
    }

}
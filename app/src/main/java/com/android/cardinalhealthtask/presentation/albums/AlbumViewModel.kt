package com.android.cardinalhealthtask.presentation.albums

import androidx.lifecycle.MutableLiveData
import com.android.cardinalhealthtask.domain.model.Album
import com.android.cardinalhealthtask.domain.model.AlbumItem
import com.android.cardinalhealthtask.domain.model.ErrorModel
import com.android.cardinalhealthtask.domain.usecase.GetAlbumsUseCase
import com.android.cardinalhealthtask.domain.usecase.GetPhotosUseCase
import com.android.cardinalhealthtask.domain.usecase.base.UseCaseResponse
import com.android.cardinalhealthtask.presentation.base.BaseViewModel

class AlbumViewModel constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseViewModel() {

    val albumData = MutableLiveData<List<Album>>()
    val photosData = MutableLiveData<List<AlbumItem>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()
    val clickedAlbum = MutableLiveData<Album>()

    fun getAlbums() {
        showProgressbar.value = true
        getAlbumsUseCase.invoke(null, object : UseCaseResponse<List<Album>> {
            override fun onSuccess(result: List<Album>) {
                albumData.value = result
                showProgressbar.value = false
            }

            override fun onError(errorModel: ErrorModel?) {
                messageData.value = errorModel?.message
                showProgressbar.value = false
            }
        })
    }

    fun getPhotos(id: String) {
        showProgressbar.value = true
        getPhotosUseCase.invoke(id, object : UseCaseResponse<List<AlbumItem>> {
            override fun onError(errorModel: ErrorModel?) {
                messageData.value = errorModel?.message
                showProgressbar.value = false
            }

            override fun onSuccess(result: List<AlbumItem>) {
                photosData.value = result
                showProgressbar.value = false
            }
        })
    }

    companion object {
        private val TAG = AlbumViewModel::class.java.name
    }

}
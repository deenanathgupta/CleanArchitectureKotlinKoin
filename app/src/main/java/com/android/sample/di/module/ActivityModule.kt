package com.android.sample.di.module

import com.android.sample.presentation.albums.AlbumViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AlbumModule = module {
    viewModel {
        AlbumViewModel(
            get(),
            get()
        )
    }

    single { createGetAlbumsUseCase(get(), createApiErrorHandle()) }

    single { createGetPhotosUseCase(get(), createApiErrorHandle()) }

    single { createAlbumRepository(get()) }
}
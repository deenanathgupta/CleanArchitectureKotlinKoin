package com.android.cardinalhealthtask.di.module

import com.android.cardinalhealthtask.data.remote.ApiService
import com.android.cardinalhealthtask.data.repository.AlbumRepositoryImp
import com.android.cardinalhealthtask.domain.exception.ApiErrorHandle
import com.android.cardinalhealthtask.domain.repository.AlbumRepository
import com.android.cardinalhealthtask.domain.usecase.GetAlbumsUseCase
import com.android.cardinalhealthtask.domain.usecase.GetPhotosUseCase
import com.cardinalhealth.sample.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.BASE_URL) }

    single { createOkHttpClient() }

    single { createMoshiConverterFactory() }

    single { createMoshi() }
}


fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}


fun createMoshi(): Moshi {
    return Moshi.Builder().build()
}

fun createMoshiConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}


fun createAlbumRepository(apiService: ApiService): AlbumRepository {
    return AlbumRepositoryImp(apiService)
}

fun createApiErrorHandle(): ApiErrorHandle {
    return ApiErrorHandle()
}

fun createGetAlbumsUseCase(
    postsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
): GetAlbumsUseCase {
    return GetAlbumsUseCase(postsRepository, apiErrorHandle)
}

fun createGetPhotosUseCase(
    postsRepository: AlbumRepository,
    apiErrorHandle: ApiErrorHandle
): GetPhotosUseCase {
    return GetPhotosUseCase(postsRepository, apiErrorHandle)
}


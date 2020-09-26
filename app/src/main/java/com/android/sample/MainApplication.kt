package com.android.sample

import android.app.Application
import com.android.sample.di.module.AlbumModule
import com.android.sample.di.module.NetworkModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(AlbumModule, NetworkModule))
        }

    }
}
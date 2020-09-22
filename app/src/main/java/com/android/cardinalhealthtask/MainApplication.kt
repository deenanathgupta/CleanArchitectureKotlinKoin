package com.android.cardinalhealthtask

import android.app.Application
import com.android.cardinalhealthtask.di.module.AlbumModule
import com.android.cardinalhealthtask.di.module.NetworkModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
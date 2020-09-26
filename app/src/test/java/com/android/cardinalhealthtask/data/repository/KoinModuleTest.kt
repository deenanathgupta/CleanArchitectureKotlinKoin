package com.android.cardinalhealthtask.data.repository

import com.android.sample.di.module.AlbumModule
import com.android.sample.di.module.NetworkModule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

class KoinModuleTest : AutoCloseKoinTest() {

    @Test
    fun testCoreModule() {
        koinApplication {
            modules(listOf(AlbumModule, NetworkModule))
        }.checkModules()
    }

}
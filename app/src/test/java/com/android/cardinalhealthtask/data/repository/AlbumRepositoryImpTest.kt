package com.android.cardinalhealthtask.data.repository

import com.android.sample.data.repository.AlbumRepositoryImp
import com.android.sample.domain.model.Album
import com.android.sample.domain.model.AlbumItem
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test


class AlbumRepositoryImpTest {

    @MockK
    lateinit var albumRepository: AlbumRepositoryImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getAlbumData() = runBlocking {
        val albums = mockk<List<Album>>()
        every { runBlocking { albumRepository.getAlbums() } } returns (albums)

        val result = albumRepository.getAlbums()
        MatcherAssert.assertThat(
            "Album Received result [$result] & mocked [$albums] must be matches on each other!",
            result,
            CoreMatchers.`is`(albums)
        )
    }

    @Test
    fun getPhotosData() = runBlocking {
        val photos = mockk<List<AlbumItem>>()
        every { runBlocking { albumRepository.getPhotos("1") } } returns (photos)

        val result = albumRepository.getPhotos("1")
        MatcherAssert.assertThat(
            "Photo Received result [$result] & mocked [$photos] must be matches on each other!",
            result,
            CoreMatchers.`is`(photos)
        )
    }
}
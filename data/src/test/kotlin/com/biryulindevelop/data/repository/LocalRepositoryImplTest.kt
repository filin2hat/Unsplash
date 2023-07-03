package com.biryulindevelop.data.repository

import androidx.paging.PagingSource
import com.biryulindevelop.data.db.PhotosDao
import com.biryulindevelop.domain.entity.PhotoEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LocalRepositoryImplTest {

    @Mock
    private lateinit var mockPhotosDao: PhotosDao

    private lateinit var repository: LocalRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = LocalRepositoryImpl(mockPhotosDao)
    }

    @Test
    fun testInsertData() = runBlocking {
        val data = listOf(
            PhotoEntity(
                photoId = "1",
                smallUrls = "Photo 1",
                likedByUser = true,
                counterLikes = 55,
                userName = "User 1",
                profileImage = "Photo 1"

            ), PhotoEntity(
                photoId = "2",
                smallUrls = "Photo 2",
                likedByUser = true,
                counterLikes = 5,
                userName = "User 2",
                profileImage = "Photo 3"
            )
        )

        repository.insertData(data)

        verify(mockPhotosDao).insert(data)
    }

    @Test
    fun testGetPagingData() {
        val pagingSource = mock<PagingSource<Int, PhotoEntity>>()
        `when`(mockPhotosDao.getPhotos()).thenReturn(pagingSource)

        val result = repository.getPagingData()

        assertEquals(pagingSource, result)
    }

    @Test
    fun testClear() = runBlocking {
        repository.clear()

        verify(mockPhotosDao).deleteAll()
    }

    @Test
    fun testSetLikeInDataBase() = runBlocking {
        val photoEntity = PhotoEntity(
            photoId = "1",
            smallUrls = "Photo 1",
            likedByUser = true,
            counterLikes = 55,
            userName = "User 1",
            profileImage = "Photo 1"

        )

        repository.setLikeInDataBase(photoEntity)

        verify(mockPhotosDao).updateLocalLikes(photoEntity)
    }

    @Test
    fun testRefresh() = runBlocking {
        val data =
            listOf(
                PhotoEntity(
                    photoId = "1",
                    smallUrls = "Photo 1",
                    likedByUser = true,
                    counterLikes = 55,
                    userName = "User 1",
                    profileImage = "Photo 1"

                ), PhotoEntity(
                    photoId = "2",
                    smallUrls = "Photo 2",
                    likedByUser = true,
                    counterLikes = 5,
                    userName = "User 2",
                    profileImage = "Photo 3"
                )
            )

        repository.refresh(data)

        verify(mockPhotosDao).refresh(data)
    }
}
package com.biryulindevelop.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.domain.model.DigestTag
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DigestPagingSourceTest {

    @Mock
    private lateinit var mockRepository: DigestRemoteRepository

    private lateinit var pagingSource: DigestPagingSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        pagingSource = DigestPagingSource(mockRepository)
    }

    @Test
    fun testGetRefreshKey() {
        val state = PagingState<Int, Digest>(
            listOf(),
            null,
            PagingConfig(10),
            20
        )
        val refreshKey = pagingSource.getRefreshKey(state)
        assertEquals(DigestPagingSource.FIRST_PAGE, refreshKey)
    }

    @Test
    fun testLoadSuccess() = runBlocking {
        val currentPage = 1
        val digests = listOf(
            Digest(
                id = "1",
                title = "Digest 1",
                userUsername = "username 1",
                description = "Description 1",
                previewPhoto = "preview 1",
                tags = listOf(DigestTag("Title 1")),
                totalPhotos = 10,
                userProfileImage = "image 1",
            ), Digest(
                id = "2",
                title = "Digest 2",
                userUsername = "username 2",
                description = "Description 2",
                previewPhoto = "preview 2",
                tags = listOf(DigestTag("Title 2")),
                totalPhotos = 20,
                userProfileImage = "image 2",
            )
        )
        val loadParams = PagingSource.LoadParams.Refresh(currentPage, 10, false)
        `when`(mockRepository.getDigests(currentPage)).thenReturn(digests)

        val result = pagingSource.load(loadParams)

        assertEquals(PagingSource.LoadResult.Page(digests, null, currentPage + 1), result)
    }
}
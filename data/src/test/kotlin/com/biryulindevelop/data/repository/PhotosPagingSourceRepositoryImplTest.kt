package com.biryulindevelop.data.repository

import com.biryulindevelop.domain.dto.photo.PhotoDetailsDto
import com.biryulindevelop.domain.dto.photo.PhotoDto
import com.biryulindevelop.domain.dto.photo.UrlsDto
import com.biryulindevelop.domain.dto.photo.UserDto
import com.biryulindevelop.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.domain.entity.PhotoEntity
import com.biryulindevelop.domain.repository.LocalRepository
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PhotosPagingSourceRepositoryImplTest {

    private lateinit var repository: PhotosPagingSourceRepositoryImpl
    private lateinit var mockPhotoRemoteRepository: PhotoRemoteRepository
    private lateinit var mockLocalRepository: LocalRepository

    @Before
    fun setup() {
        mockPhotoRemoteRepository = mockk()
        mockLocalRepository = mockk()
        repository =
            PhotosPagingSourceRepositoryImpl(mockPhotoRemoteRepository, mockLocalRepository)
    }

    @Test
    fun testSetLike() = runBlocking {
        val id = "1"
        val wrapperPhotoDto = WrapperPhotoDto(
            PhotoDto(
                id = "1",
                UrlsDto(
                    full = "full",
                    raw = "raw",
                    regular = "regular",
                    small = "small",
                    smallS3 = "smallS3",
                    thumb = "thumb  "
                ),
                likedByUser = false,
                likes = 1,
                user = UserDto(
                    username = "username",
                    name = "name",
                    bio = "bio",
                    profileImage = UserDto.ProfileImageDto(
                        large = "large",
                        medium = "medium",
                        small = "small"
                    )
                ),
                height = 1,
                width = 1,
                links = PhotoDetailsDto.LinksDto(
                    download = "download",
                    downloadLocation = "downloadLocation",
                    html = "html",
                    self = "self"
                )
            )
        )
        coEvery { mockPhotoRemoteRepository.likePhoto(id) } returns wrapperPhotoDto

        val result = repository.setLike(id)

        assertEquals(wrapperPhotoDto, result)
    }

    @Test
    fun testDeleteLike() = runBlocking {
        val id = "1"
        val wrapperPhotoDto = WrapperPhotoDto(
            PhotoDto(
                id = "1",
                UrlsDto(
                    full = "full",
                    raw = "raw",
                    regular = "regular",
                    small = "small",
                    smallS3 = "smallS3",
                    thumb = "thumb  "
                ),
                likedByUser = false,
                likes = 1,
                user = UserDto(
                    username = "username",
                    name = "name",
                    bio = "bio",
                    profileImage = UserDto.ProfileImageDto(
                        large = "large",
                        medium = "medium",
                        small = "small"
                    )
                ),
                height = 1,
                width = 1,
                links = PhotoDetailsDto.LinksDto(
                    download = "download",
                    downloadLocation = "downloadLocation",
                    html = "html",
                    self = "self"
                )
            )
        )
        coEvery { mockPhotoRemoteRepository.unlikePhoto(id) } returns wrapperPhotoDto

        val result = repository.deleteLike(id)

        assertEquals(wrapperPhotoDto, result)
    }

    @Test
    fun testUpdateLikeDB(): Unit = runBlocking {
        val entity = PhotoEntity(
            photoId = "1",
            smallUrls = "Photo 1",
            likedByUser = true,
            counterLikes = 55,
            userName = "User 1",
            profileImage = "Photo 1"

        )

        repository.updateLikeDB(entity)

        coEvery { mockLocalRepository.setLikeInDataBase(entity) } returns Unit
    }
}
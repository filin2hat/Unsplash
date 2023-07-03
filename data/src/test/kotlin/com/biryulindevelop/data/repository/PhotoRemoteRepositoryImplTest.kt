package com.biryulindevelop.data.repository

import com.biryulindevelop.data.api.ApiDigest
import com.biryulindevelop.data.api.ApiPhotos
import com.biryulindevelop.data.api.ApiProfile
import com.biryulindevelop.domain.dto.photo.PhotoDetailsDto
import com.biryulindevelop.domain.dto.photo.PhotoDto
import com.biryulindevelop.domain.dto.photo.PhotoListDto
import com.biryulindevelop.domain.dto.photo.UrlsDto
import com.biryulindevelop.domain.dto.photo.UserDto
import com.biryulindevelop.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.state.Requester
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PhotoRemoteRepositoryImplTest {

    @Mock
    private lateinit var mockApiPhotos: ApiPhotos

    @Mock
    private lateinit var mockApiDigest: ApiDigest

    @Mock
    private lateinit var mockApiProfile: ApiProfile

    private lateinit var repository: PhotoRemoteRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = PhotoRemoteRepositoryImpl(mockApiPhotos, mockApiDigest, mockApiProfile)
    }

    @Test
    fun testGetPhotoListAllList() = runBlocking {
        val requester = Requester.ALL_LIST
        val page = 1
        val photoListDto = PhotoListDto(
        )
        `when`(mockApiPhotos.getPhotos(page)).thenReturn(photoListDto)

        val result = repository.getPhotoList(requester, page)

        assertEquals(photoListDto, result)
    }

    @Test
    fun testGetPhotoDetails() = runBlocking {
        val id = "photo_id"
        val photoDetailsDto = PhotoDetailsDto(
            id = "1",
            downloads = 1,
            likes = 1,
            likedByUser = true,
            exif = PhotoDetailsDto.ExifDto(
                make = "make",
                model = "model",
                name = "name",
                exposureTime = "exposureTime",
                aperture = "aperture",
                focalLength = "focalLength",
                iso = 100
            ),
            location = PhotoDetailsDto.LocationDto(
                city = "city",
                country = "country",
                position = PhotoDetailsDto.LocationDto.PositionDto(
                    latitude = 1.0,
                    longitude = 1.0
                )
            ),
            tags = listOf(),
            urls = UrlsDto(
                full = "full",
                raw = "raw",
                regular = "regular",
                small = "small",
                smallS3 = "smallS3",
                thumb = "thumb  "

            ),
            links = PhotoDetailsDto.LinksDto(
                download = "download",
                downloadLocation = "downloadLocation",
                html = "html",
                self = "self"
            ),
            user = UserDto(
                username = "username",
                name = "name",
                bio = "bio",
                profileImage = UserDto.ProfileImageDto(
                    large = "large",
                    medium = "medium",
                    small = "small"
                )
            )
        )
        `when`(mockApiPhotos.getPhotoDetails(id)).thenReturn(photoDetailsDto)

        val result = repository.getPhotoDetails(id)

        assertEquals(photoDetailsDto, result)
    }

    @Test
    fun testLikePhoto() = runBlocking {
        val id = "photo_id"
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
        `when`(mockApiPhotos.like(id)).thenReturn(wrapperPhotoDto)

        val result = repository.likePhoto(id)

        assertEquals(wrapperPhotoDto, result)
    }

    @Test
    fun testUnlikePhoto() = runBlocking {
        val id = "photo_id"
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
        `when`(mockApiPhotos.unlike(id)).thenReturn(wrapperPhotoDto)

        val result = repository.unlikePhoto(id)

        assertEquals(wrapperPhotoDto, result)
    }
}
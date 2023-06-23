package com.biryulindevelop.unsplash.data.repository

import com.biryulindevelop.unsplash.data.api.ApiDigest
import com.biryulindevelop.unsplash.data.api.ApiPhotos
import com.biryulindevelop.unsplash.data.api.ApiProfile
import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoDetailsDto
import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoListDto
import com.biryulindevelop.unsplash.data.api.dto.photo.WrapperPhotoDto
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.repository.PhotoRemoteRepository
import javax.inject.Inject

class PhotoRemoteRepositoryImpl @Inject constructor(
    private val apiPhotos: ApiPhotos,
    private val apiDigest: ApiDigest,
    private val apiProfile: ApiProfile
) :
    PhotoRemoteRepository {
    override suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto {
        return when (requester) {
            Requester.ALL_LIST -> checkRequester(requester.param, page)
            Requester.COLLECTIONS -> apiDigest.getDigestPhotos(requester.param, page)
            Requester.PROFILE -> apiProfile.getProfileLikes(requester.param, page)
        }
    }

    private suspend fun checkRequester(query: String, page: Int) =
        if (query == "") apiPhotos.getPhotos(page)
        else apiPhotos.searchPhotos(query, page).results

    override suspend fun getPhotoDetails(id: String): PhotoDetailsDto {
        return apiPhotos.getPhotoDetails(id)
    }

    override suspend fun likePhoto(id: String): WrapperPhotoDto {
        return apiPhotos.like(id)
    }

    override suspend fun unlikePhoto(id: String): WrapperPhotoDto {
        return apiPhotos.unlike(id)
    }
}

package com.biryulindevelop.data.repository

import com.biryulindevelop.data.api.ApiDigest
import com.biryulindevelop.data.api.ApiPhotos
import com.biryulindevelop.data.api.ApiProfile
import com.biryulindevelop.domain.dto.photo.PhotoDetailsDto
import com.biryulindevelop.domain.dto.photo.PhotoListDto
import com.biryulindevelop.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.state.Requester
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

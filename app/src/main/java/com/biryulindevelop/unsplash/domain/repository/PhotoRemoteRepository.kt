package com.biryulindevelop.unsplash.domain.repository

import com.biryulindevelop.unsplash.data.api.photodto.PhotoDetailsDto
import com.biryulindevelop.unsplash.data.api.photodto.PhotoListDto
import com.biryulindevelop.unsplash.data.api.photodto.WrapperPhotoDto
import com.biryulindevelop.unsplash.data.state.Requester

interface PhotoRemoteRepository {

    suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto
}
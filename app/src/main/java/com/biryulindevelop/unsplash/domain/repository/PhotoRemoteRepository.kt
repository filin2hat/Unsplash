package com.biryulindevelop.unsplash.domain.repository

import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoDetailsDto
import com.biryulindevelop.unsplash.data.api.dto.photo.PhotoListDto
import com.biryulindevelop.unsplash.data.api.dto.photo.WrapperPhotoDto
import com.biryulindevelop.unsplash.data.state.Requester

interface PhotoRemoteRepository {

    suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto
}
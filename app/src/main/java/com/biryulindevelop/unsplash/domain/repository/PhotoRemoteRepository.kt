package com.biryulindevelop.unsplash.domain.repository

import com.biryulindevelop.unsplash.domain.dto.photo.PhotoDetailsDto
import com.biryulindevelop.unsplash.domain.dto.photo.PhotoListDto
import com.biryulindevelop.unsplash.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.unsplash.domain.state.Requester

interface PhotoRemoteRepository {

    suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto
}
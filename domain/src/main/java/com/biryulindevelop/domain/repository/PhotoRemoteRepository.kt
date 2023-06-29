package com.biryulindevelop.domain.repository

import com.biryulindevelop.domain.dto.photo.PhotoDetailsDto
import com.biryulindevelop.domain.dto.photo.PhotoListDto
import com.biryulindevelop.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.domain.state.Requester

interface PhotoRemoteRepository {

    suspend fun getPhotoList(requester: Requester, page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto
}
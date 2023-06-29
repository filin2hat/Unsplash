package com.biryulindevelop.unsplash.domain.repository

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.domain.dto.photo.WrapperPhotoDto
import com.biryulindevelop.unsplash.domain.entity.PhotoEntity
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.domain.state.Requester
import kotlinx.coroutines.flow.Flow

interface PhotosPagingSourceRepository {

    fun getFlowPhoto(requester: Requester): Flow<PagingData<Photo>>

    suspend fun setLike(id: String): WrapperPhotoDto

    suspend fun deleteLike(id: String): WrapperPhotoDto

    suspend fun updateLikeDB(entity: PhotoEntity)
}
package com.biryulindevelop.unsplash.domain.repository

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.data.api.photodto.WrapperPhotoDto
import com.biryulindevelop.unsplash.data.local.entity.PhotoEntity
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosPagingSourceRepository {

    fun getFlowPhoto(requester: Requester): Flow<PagingData<Photo>>

    suspend fun setLike(id: String): WrapperPhotoDto

    suspend fun deleteLike(id: String): WrapperPhotoDto

    suspend fun updateLikeDB(entity: PhotoEntity)
}
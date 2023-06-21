package com.biryulindevelop.unsplash.domain.usecase

import androidx.paging.PagingData
import com.biryulindevelop.unsplash.data.state.Requester
import com.biryulindevelop.unsplash.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosPagingUseCase {

    fun getPhoto(requester: Requester): Flow<PagingData<Photo>>

}
